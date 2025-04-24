package com.zaur.nodetest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaur.nodetest.data.NodeDataStore
import com.zaur.nodetest.model.DisplayNodeName
import com.zaur.nodetest.model.Node
import com.zaur.nodetest.observable.NodeObservable
import com.zaur.nodetest.observable.NodeUIState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

interface NodeViewModel : NodeObservable.Read {

    fun addChild(parentId: String)
    fun deleteNode(parentId: String)

    class Base(
        private val dataStore: NodeDataStore,
        private val displayNodeName: DisplayNodeName,
        private val observable: NodeObservable.Mutable,
    ) : ViewModel(), NodeViewModel {

        init {
            viewModelScope.launch {
                val loaded = dataStore.loadTree()
                val withRoot = if (loaded.isEmpty()) {
                    // если нет ни одного node — создаём корневой
                    listOf(Node(id = "root", name = "Root", parentId = ""))
                } else loaded
                observable.update(observable.state().value.copy(nodes = withRoot))
            }
        }

        override fun nodeState(): StateFlow<NodeUIState> = observable.nodeState()

        override fun addChild(parentId: String) {
            viewModelScope.launch {
                val current = observable.state().value.nodes

                // Рекурсивно создаём новый список с добавленным node
                fun recurse(list: List<Node>): List<Node> = list.map { node ->
                    if (node.id == parentId) {
                        val added = Node(
                            id = UUID.randomUUID().toString(),
                            name = displayNodeName.displayName(node),
                            parentId = parentId
                        )
                        node.copy(child = node.child + added)
                    } else {
                        val newChildren = recurse(node.child)
                        if (newChildren === node.child) {
                            node  // возвращаем старый объект, если child не изменились
                        } else {
                            node.copy(child = newChildren)
                        }
                    }
                }

                val updated = recurse(current)
                dataStore.saveTree(updated)
                observable.update(observable.state().value.copy(nodes = updated))
            }
        }

        override fun deleteNode(nodeId: String) {
            viewModelScope.launch {
                fun recurse(list: List<Node>): List<Node> = list.flatMap { node ->
                    if (node.id == nodeId) {
                        emptyList() // удаляем этот node
                    } else {
                        listOf(node.copy(child = recurse(node.child)))
                    }
                }

                val updated = recurse(observable.state().value.nodes)
                dataStore.saveTree(updated)
                observable.update(observable.state().value.copy(nodes = updated))
            }
        }
    }

}
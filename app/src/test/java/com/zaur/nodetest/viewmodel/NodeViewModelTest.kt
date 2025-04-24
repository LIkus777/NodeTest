package com.zaur.nodetest.viewmodel

import com.zaur.nodetest.data.NodeDataStore
import com.zaur.nodetest.model.DisplayNodeName
import com.zaur.nodetest.model.Node
import com.zaur.nodetest.observable.NodeObservable
import com.zaur.nodetest.observable.NodeUIState
import com.zaur.test.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class NodeViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private lateinit var dataStore: FakeNodeDataStore
    private lateinit var vm: NodeViewModel.Base
    private val testScope = TestScope(mainCoroutineRule.dispatcher)

    @Before
    fun setUp() {
        dataStore = FakeNodeDataStore()
        val displayName = DisplayNodeName.Base()
        val observable = NodeObservable.Base(NodeUIState(nodes = emptyList()))
        vm = NodeViewModel.Base(dataStore, displayName, observable)
    }
    @Test
    fun testRootInit(){
        testScope.runTest {
            advanceUntilIdle()
        }
    }
    @Test
    fun `initial state contains root only`() = testScope.runTest {
        val nodes = vm.nodeState().value.nodes
        assertEquals(1, nodes.size)
        assertEquals("root", nodes[0].id)
    }

    @Test
    fun `addChild adds one child to root`() = testScope.runTest {
        vm.addChild("root")
        advanceUntilIdle()

        val nodes = vm.nodeState().value.nodes
        assertEquals(1, nodes.size)
        val root = nodes[0]
        assertEquals("root", root.id)
        assertEquals(1, root.child.size)
        // ребёнок получил name из хэша, проверим что id не пустой
        assertTrue(root.child[0].id.isNotBlank())
        assertEquals(root.id, root.child[0].parentId)
    }
    @Test
    fun `deleteNode removes the child`() = testScope.runTest {
        // сначала добавим
        vm.addChild("root")
        advanceUntilIdle()

        val childId = vm.nodeState().value.nodes[0].child[0].id

        // удалим
        vm.deleteNode(childId)
        advanceUntilIdle()

        val nodes = vm.nodeState().value.nodes
        assertEquals(1, nodes.size)
        assertTrue(nodes[0].child.isEmpty())
    }
}

class FakeNodeDataStore : NodeDataStore {
    private var storage: List<Node> = emptyList()
    override suspend fun loadTree(): List<Node> = storage
    override suspend fun saveTree(nodes: List<Node>) {
        storage = nodes
    }
}
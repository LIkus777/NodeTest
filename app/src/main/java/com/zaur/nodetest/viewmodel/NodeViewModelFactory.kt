package com.zaur.nodetest.viewmodel

import com.zaur.nodetest.data.NodeDataStore
import com.zaur.nodetest.model.DisplayNodeName
import com.zaur.nodetest.observable.NodeObservable
import com.zaur.nodetest.observable.NodeUIState

interface NodeViewModelFactory {

    fun create(): NodeViewModel

    class Base(
        private val dataStore: NodeDataStore,
        private val displayNodeName: DisplayNodeName,
        private val observable: NodeObservable.Mutable = NodeObservable.Base(NodeUIState())
    ) : NodeViewModelFactory {
        override fun create(): NodeViewModel = NodeViewModel.Base(dataStore, displayNodeName, observable)
    }

}
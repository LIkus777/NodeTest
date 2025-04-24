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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NodeViewModelFactoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private val testScope = TestScope(mainCoroutineRule.dispatcher)

    private lateinit var dataStore: NodeDataStore
    private lateinit var displayNodeName: DisplayNodeName
    private lateinit var observable: NodeObservable.Mutable
    private lateinit var factory: NodeViewModelFactory.Base

    @Before
    fun setUp() {
        dataStore = FakeNodeDataStore()
        displayNodeName = FakeDisplayNodeName()
        observable = NodeObservable.Base(NodeUIState(nodes = emptyList()))
        factory = NodeViewModelFactory.Base(dataStore, displayNodeName, observable)
    }

    @Test
    fun `create returns a NodeViewModel instance`() {
        val viewModel = factory.create()
        assertNotNull(viewModel)
        assertTrue(viewModel is NodeViewModel.Base)
    }

    @Test
    fun `create returns NodeViewModel with correct dependencies and loads data`() =
        testScope.runTest {
            val viewModel = factory.create() as NodeViewModel.Base
            assertNotNull(viewModel)
            advanceUntilIdle()
        }
}


class FakeDisplayNodeName : DisplayNodeName {
    private var nodeName: String = ""

    override fun displayName(node: Node): String {
        nodeName = node.name
        return nodeName
    }
}
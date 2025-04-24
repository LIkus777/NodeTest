package com.zaur.nodetest.viewmodel

import com.zaur.nodetest.observable.Observable.Abstract
import com.zaur.test.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class NodeObservableTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private val testScope = TestScope(mainCoroutineRule.dispatcher)
    private lateinit var observable: Abstract<String>

    @Before
    fun setUp() {
        observable = BaseTest("initial")
    }

    @Test
    fun `Abstract class updates state correctly`() = testScope.runTest {

        observable.update("updated")
        advanceUntilIdle()

        assertEquals("updated", observable.state().value)
    }

    @Test
    fun `Abstract class returns state correctly`() = testScope.runTest {

        val state = observable.state()
        advanceUntilIdle()

        assertEquals("initial", state.value)
    }
}

class BaseTest(initial: String) : Abstract<String>(initial)
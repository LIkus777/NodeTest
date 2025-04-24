package com.zaur.nodetest.observable

import kotlinx.coroutines.flow.StateFlow

interface NodeObservable : Observable.Mutable<NodeUIState> {

    interface Update : Observable.Update<NodeUIState>

    interface Read : Observable.Read<NodeUIState> {
        fun nodeState(): StateFlow<NodeUIState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: NodeUIState
    ) : Observable.Abstract<NodeUIState>(initial), Mutable {
        override fun nodeState(): StateFlow<NodeUIState> = state()
    }

}
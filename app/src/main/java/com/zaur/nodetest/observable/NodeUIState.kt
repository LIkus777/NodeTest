package com.zaur.nodetest.observable

import com.zaur.nodetest.model.Node

data class NodeUIState(
    val isLoading: Boolean = false,
    val nodes: List<Node> = emptyList(),
)

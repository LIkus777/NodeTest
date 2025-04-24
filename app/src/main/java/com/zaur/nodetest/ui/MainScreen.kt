package com.zaur.nodetest.ui

import androidx.compose.runtime.Composable
import com.zaur.nodetest.model.Node
import com.zaur.nodetest.navigation.Screen
import com.zaur.nodetest.navigation.TreeNavHost

@Composable
fun MainScreen(
    allNodes: List<Node>,
    onAddChild: (String) -> Unit,
    onDeleteNode: (String) -> Unit,
) {
    TreeNavHost { navController, parentId ->
        TreeScreen(rootId = parentId, allNodes = allNodes, onNodeClick = { childId ->
            navController.navigate(Screen.TreeNode.createRoute(childId))
        }, onAddChild = { pid -> onAddChild(pid) }, onDeleteNode = { nid ->
            onDeleteNode(nid)
            val parent = allNodes.firstOrNull { it.id == nid }?.parentId
            if (!parent.isNullOrEmpty()) navController.popBackStack()
        })
    }
}
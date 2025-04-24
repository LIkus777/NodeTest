package com.zaur.nodetest.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object TreeNode : Screen("tree/{parentId}") {
        // Метод для создания node
        fun createRoute(parentId: String) = "tree/${Uri.encode(parentId)}"
    }
}
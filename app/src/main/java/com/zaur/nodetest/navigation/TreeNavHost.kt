package com.zaur.nodetest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun TreeNavHost(
    treeScreen: @Composable (NavHostController, String) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Screen.TreeNode.createRoute("root")
    ) {
        composable(
            route = Screen.TreeNode.route, arguments = listOf(navArgument("parentId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val parentId = backStackEntry.arguments?.getString("parentId") ?: "root"
            treeScreen(navController, parentId)
        }
    }
}
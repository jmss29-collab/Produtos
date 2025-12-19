package com.example.produtos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.produtos.ui.screens.ProductDetailScreen
import com.example.produtos.ui.screens.ProductListScreen
import com.example.produtos.ui.screens.SettingsScreen
import com.example.produtos.ui.viewmodel.SettingsViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "list"
    ) {

        composable("list") {
            ProductListScreen(
                onItemClick = { id ->
                    navController.navigate("details/$id")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                settingsViewModel = settingsViewModel
            )
        }

        composable("settings") {
            SettingsScreen(
                navController = navController,
                viewModel = settingsViewModel
            )
        }

        composable("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            ProductDetailScreen(
                productId = id,
                navController = navController
            )
        }
    }
}

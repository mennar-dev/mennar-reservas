package com.mennarsas.myapplication.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mennarsas.myapplication.ui.MainScreen
import com.mennarsas.myapplication.ui.screens.login.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavGraph.Auth.Login.route) {
        composable(NavGraph.Auth.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(NavGraph.Main.route) {
                    popUpTo(NavGraph.Auth.Login.route) { inclusive = true }
                }
            })
        }

        composable(NavGraph.Main.route) {
            MainScreen(
                onLogout = {
                    navController.navigate(NavGraph.Auth.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

    }
}
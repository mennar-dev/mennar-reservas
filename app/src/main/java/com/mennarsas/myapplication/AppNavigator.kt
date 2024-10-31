package com.mennarsas.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mennarsas.myapplication.ui.screens.login.ui.LoginScreen
import com.mennarsas.myapplication.ui.screens.home.ui.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("home")
            })
        }
        composable("home") { HomeScreen() }
    }
}
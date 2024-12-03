package com.mennarsas.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mennarsas.myapplication.ui.MainScreen
import com.mennarsas.myapplication.ui.screens.auth.SplashScreen
import com.mennarsas.myapplication.ui.screens.auth.login.LoginScreen
import com.mennarsas.myapplication.ui.screens.auth.recoverypassword.RecoverPasswordScreen
import com.mennarsas.myapplication.ui.screens.auth.signup.SignUpScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavGraph.Auth.Splash.route) {

        composable(NavGraph.Auth.Splash.route) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(NavGraph.Auth.Login.route) {
                        popUpTo(NavGraph.Auth.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(NavGraph.Auth.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(NavGraph.Main.route) {
                    popUpTo(NavGraph.Auth.Login.route) { inclusive = true }
                }
            },
                onSignUpClick = {
                    navController.navigate(NavGraph.Auth.SignUp.route)
               },
                onRecoverPasswordClick = {
                    navController.navigate(NavGraph.Auth.RecoveryPassword.route)
                }
            )
        }

        composable(NavGraph.Auth.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(NavGraph.Main.route) {
                        popUpTo(NavGraph.Auth.Login.route) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.navigateUp()
                }
            )
        }

        composable(NavGraph.Auth.RecoveryPassword.route) {
            RecoverPasswordScreen(
                onBackToLogin = {
                    navController.navigateUp()
                }
            )
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
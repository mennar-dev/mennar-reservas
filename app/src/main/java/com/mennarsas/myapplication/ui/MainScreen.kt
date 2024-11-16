package com.mennarsas.myapplication.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.mennarsas.myapplication.Navigation.MainNavHost
import com.mennarsas.myapplication.ui.components.Header
import com.mennarsas.myapplication.ui.components.NavigationMenu

@Composable
fun MainScreen(
    onLogout: () -> Unit,
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            Header(onLogout)
        },
        bottomBar = {
          NavigationMenu(navController = navController)
        }
    ) { paddingValues ->
        MainNavHost(navController, paddingValues)
    }
}
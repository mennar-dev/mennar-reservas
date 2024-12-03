package com.mennarsas.myapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mennarsas.myapplication.ui.screens.home.ReservarScreen
import com.mennarsas.myapplication.ui.screens.my_reservations.MyReservationsScreen

@Composable
fun MainNavHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
        modifier = Modifier.padding(paddingValues)
    ){
        composable(NavigationItem.Home.route) {
            ReservarScreen(navController = navController)
        }
        composable(NavigationItem.MyReservations.route){
            MyReservationsScreen()
        }
    }
}
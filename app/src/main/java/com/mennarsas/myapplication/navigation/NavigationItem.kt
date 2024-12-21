package com.mennarsas.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

// Clase para representar las opciones de navegación
sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : NavigationItem(
        route = "home",
        title = "Inicio",
        icon = Icons.Rounded.Home
    )

    data object MyReservations : NavigationItem(
        route = "my_reservations",
        title = "Mis reservas",
        icon = Icons.Default.AccountBox
    )

    companion object {
        fun getItems() = listOf(Home, MyReservations)
    }
}
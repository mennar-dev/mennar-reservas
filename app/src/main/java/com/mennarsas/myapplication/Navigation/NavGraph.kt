package com.mennarsas.myapplication.Navigation

sealed class NavGraph(val route: String){

    // Clase para representar las pantallas de autenticación
    data object Auth : NavGraph("auth"){
        data object Login : NavGraph("login")
    }

    // Clase para representar las pantallas principales de la aplicación
    data object Main : NavGraph("main"){
        data object Reservar : NavGraph("Reservar") {
            fun createRoute() = "reservar"
        }
        data object MisReservas : NavGraph( "mis_reservas"){
            fun createRoute() = "mis_reservas"
        }
    }
}
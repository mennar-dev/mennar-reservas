package com.mennarsas.myapplication.navigation

sealed class NavGraph(val route: String){

    // Clase para representar las pantallas de autenticación
    data object Auth : NavGraph("auth"){
        data object Splash : NavGraph("splash")
        data object Login : NavGraph("login")
        data object SignUp : NavGraph("signup")
        data object RecoveryPassword : NavGraph("recovery_password")
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
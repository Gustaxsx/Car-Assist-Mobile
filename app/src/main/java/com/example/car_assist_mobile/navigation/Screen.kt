package com.example.car_assist_mobile.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Profile : Screen("profile")

    object Garage : Screen("garage")
}
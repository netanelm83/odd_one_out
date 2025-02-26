package com.netgames.oddoneout.navigation

sealed class Screen(val route : String) {
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Game : Screen("game")
}

package com.example.bihm.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object Player : Screen("player")
    data object Settings : Screen("settings")
}

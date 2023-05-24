package com.iedrania.mortypedia.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object About : Screen("about")
}
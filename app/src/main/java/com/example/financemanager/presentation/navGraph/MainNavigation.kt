package com.example.financemanager.presentation.navGraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController


sealed class MainNavigation (val title: String){
    object Home: MainNavigation("Home")
    object Details: MainNavigation("Details")
    object Add : MainNavigation("Add")
}


val bottomNavItems = listOf(
    BottomNavItem(
        name = "Home",
        route = MainNavigation.Home.title,
        icon = Icons.Rounded.Home,
    ),
    BottomNavItem(
        name = "Create",
        route = MainNavigation.Add.title,
        icon = Icons.Rounded.AddCircle,
    ),
    BottomNavItem(
        name = "Settings",
        route = MainNavigation.Details.title,
        icon = Icons.Rounded.AccountCircle,
    ),
)


data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)

package com.example.financemanager.presentation.navGraph

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.financemanager.R
sealed class MainNavigation (val title: Int){
    data object Home: MainNavigation(R.string.homeTitle)
    data object Details: MainNavigation(R.string.settingsTitle)
    data object Add : MainNavigation(R.string.createTitle)
}
fun getBottomNavItems(context: Context): List<BottomNavItem>{
    return listOf(
        BottomNavItem(
            name = context.getString(R.string.homeTitle),
            route = context.getString(R.string.homeTitle),
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = context.getString(R.string.createTitle),
            route = context.getString(MainNavigation.Add.title),
            icon = Icons.Rounded.AddCircle,
        ),
        BottomNavItem(
            name = context.getString(R.string.settingsTitle),
            route = context.getString(MainNavigation.Details.title),
            icon = Icons.Rounded.AccountCircle,
        ),
    )
}
data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)

package com.example.financemanager.presentation.screens

import android.app.FragmentManager.BackStackEntry
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financemanager.R
import com.example.financemanager.presentation.navGraph.LoginNavigation
import com.example.financemanager.presentation.navGraph.MainNavigation
import com.example.financemanager.presentation.navGraph.bottomNavItems
import com.example.financemanager.presentation.viewModel.SignInViewModel
import com.example.financemanager.presentation.viewModel.SignUpViewModel
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainContainer(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    val hazeState = remember {
        HazeState()
    }
        // Главный экран с Bottom Navigation
        Scaffold(
            topBar = {
                if (navController.currentDestination?.route == MainNavigation.Home.title) {
                    TopAppBar(
                        title = { Text(text = "Home") },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.popBackStack() }
                            ) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    )
                }
            },
            bottomBar = {
                SampleNavigationBar(
                    navController = navController,
                    modifier = Modifier.hazeChild(hazeState)
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .haze(hazeState),
                contentPadding = it
            ) {
                item {
                    NavHost(
                        navController = navController,
                        startDestination = MainNavigation.Add.title,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(MainNavigation.Home.title ,) {
                            HomeScreen()
                        }
                        composable(MainNavigation.Add.title) {
                            AddExpensesScreen()
                        }
                        composable(MainNavigation.Details.title) {
                            DetailScreen()
                        }
                    }
                }
            }
        }

}



@Composable
private fun SampleNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomNavItems.forEachIndexed {index , item  ->

            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true} },

                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(178, 235, 242, 255),
                        selectedIconColor = Color.Blue,
                        indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(LocalAbsoluteTonalElevation.current) )
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewMainContainer(){
    mainContainer()
}





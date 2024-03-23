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
    val backStackEntry = navController.currentBackStackEntryAsState()
    val hazeState = remember {
        HazeState()
    }

    var isLoggedIn = remember { mutableStateOf(false)}

    if (!isLoggedIn.value) {
        // Экран авторизации
        Box(modifier = Modifier.fillMaxSize()) {
            Image(painter = painterResource(id = R.drawable.sign), contentDescription = "" , modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
                contentScale = ContentScale.Crop)
            NavHost(
                navController = navController,
                startDestination = LoginNavigation.Start.title,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(LoginNavigation.Start.title) {
                    StartScreen({ navController.navigate(LoginNavigation.SingIn.title) },
                        { navController.navigate(LoginNavigation.SignUp.title) })
                }
                composable(LoginNavigation.SingIn.title) {
                    val viewModel = hiltViewModel<SignInViewModel>()
                    SignInScreen({ isLoggedIn.value = true }, viewModel)
                }
                composable(LoginNavigation.SignUp.title) {
                    val viewModel = hiltViewModel<SignUpViewModel>()
                    SingUpScreen( viewModel ,{ navController.navigate(LoginNavigation.SingIn.title) })
                }

            }
        }
    } else {
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
                    backStackEntry,
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
                        startDestination = MainNavigation.Home.title,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(MainNavigation.Home.title ,) {
                            HomeScreen(Modifier)
                        }
                        composable(MainNavigation.Add.title) {

                        }
                        composable(MainNavigation.Details.title) {
                            DetailScreen(Modifier)
                        }
                    }
                }
            }
        }
    }
}



@Composable
private fun SampleNavigationBar(
    backStackEntry: State<NavBackStackEntry?>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        var selectedItem by remember { mutableStateOf(0) }
        bottomNavItems.forEachIndexed {index , item  ->

            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { selectedItem = index
                    navController.navigate(item.route){
                        popUpTo(item.route) { inclusive = true }
                    } },

                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                    )
                },
                colors = androidx.compose.material3.NavigationBarItemDefaults
                    .colors(
                        unselectedIconColor = Color(178, 235, 242, 255),
                        selectedIconColor = Color.Blue,
                        indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(LocalAbsoluteTonalElevation.current) )
            )
        }
    }
}

@Composable
fun bottomNavigationBar( navController: NavController ,modifier: Modifier){
    BottomAppBar(containerColor = Color.Transparent , modifier = modifier) {
        IconButton(onClick = { navController.navigate(MainNavigation.Home.title)} , modifier = Modifier.padding(start = 16.dp)) {
            Icon( imageVector = Icons.Filled.Home , contentDescription = "home")
        }
        
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {navController.navigate(MainNavigation.Details.title)   } , modifier = Modifier.padding(end = 16.dp)) {
            Icon(imageVector = Icons.Filled.Build  , contentDescription = "details")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainContainer(){
    mainContainer()
}
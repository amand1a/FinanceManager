package com.example.financemanager.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financemanager.R
import com.example.financemanager.presentation.navGraph.MainNavigation
import com.example.financemanager.presentation.navGraph.getBottomNavItems
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainer() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val hazeState = remember {
        HazeState()
    }
    Scaffold(
        topBar = {
            if (navController.currentDestination?.route == stringResource(id = MainNavigation.Home.title)) {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.homeTitle)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
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
                    startDestination = stringResource(MainNavigation.Add.title),
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(context.getString(MainNavigation.Home.title)) {
                        HomeScreen()
                    }
                    composable(context.getString(MainNavigation.Add.title)) {
                        AddExpensesScreen()
                    }
                    composable(context.getString(MainNavigation.Details.title)) {
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
        val bottomNavItems = getBottomNavItems(LocalContext.current)
        bottomNavItems.forEachIndexed { _, item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                    )
                },
                colors = colors(
                    indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        LocalAbsoluteTonalElevation.current
                    )
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainContainer() {
    MainContainer()
}





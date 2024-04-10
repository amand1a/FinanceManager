package com.example.financemanager.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financemanager.R
import com.example.financemanager.presentation.navGraph.MainNavigation
import com.example.financemanager.presentation.navGraph.getBottomNavItems
import com.example.financemanager.presentation.viewModel.AddExpensesViewModel
import com.example.financemanager.presentation.viewModel.HomeViewModel
import com.example.financemanager.presentation.viewModel.MainContainerViewModel
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainContainer(viewModel: MainContainerViewModel = viewModel()) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val hazeState = remember {
        HazeState()
    }
    val uiState = viewModel.uiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            CustomTopBar(
                monthName = uiState.value.selectedMonth.month.name,
                currentRoute = currentRoute ?: "",
                onPrevMonthClick = { viewModel.getPrevMoth() },
                onNextMonthClick = { viewModel.getNextMoth() },
                modifier = Modifier.hazeChild(hazeState)
            )
        },
        bottomBar = {
            SampleNavigationBar(
                navController = navController,
                currentRoute = currentRoute ?: "",
                modifier = Modifier.hazeChild(hazeState)
            )
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = stringResource(MainNavigation.Add.title),
            modifier = Modifier
                .fillMaxSize()
                .haze(hazeState)
        ) {
            composable(context.getString(MainNavigation.Home.title)) {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(
                    selectedDate = uiState.value.selectedMonth,
                    viewModel = homeViewModel,
                    contentPadding = contentPadding
                )
            }
            composable(context.getString(MainNavigation.Add.title)) {
                val addExpensesViewModel = hiltViewModel<AddExpensesViewModel>()
                AddExpensesScreen(contentPadding = contentPadding, viewModel = addExpensesViewModel)
            }
            composable(context.getString(MainNavigation.Details.title)) {
                DetailScreen(contentPadding = contentPadding)
            }
        }
    }
}

@Composable
private fun SampleNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentRoute: String
) {
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    monthName: String,
    currentRoute: String,
    onPrevMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (currentRoute == stringResource(id = MainNavigation.Home.title)) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = monthName,
                    textAlign = TextAlign.Center
                )
            },
            navigationIcon = {
                IconButton(onClick = { onNextMonthClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.empty_string)
                    )
                }
            },
            actions = {
                IconButton(onClick = { onPrevMonthClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(id = R.string.empty_string)
                    )
                }
            },
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainContainer() {
    MainContainer()
}





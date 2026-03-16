package com.amira.metro_masr.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amira.metro_masr.di.AppModule
import com.amira.metro_masr.presentation.details.RouteDetailsScreen
import com.amira.metro_masr.presentation.home.HomeScreen
import com.amira.metro_masr.presentation.home.HomeScreenViewModel
import com.amira.metro_masr.presentation.home.HomeScreenViewModelFactory
import com.amira.metro_masr.presentation.settings.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Simple manual DI for the ViewModel
    val viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModelFactory(
            AppModule.provideGetStations(context).repository,
            AppModule.provideFindRoute(context)
        )
    )

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") { MetroSplashScreen(navController) }

        composable("home_screen") {
            val state by viewModel.screenState.collectAsState()
            HomeScreen(
                state = state,
                onStartStationChange = viewModel::onStartStationChange,
                onEndStationChange = viewModel::onEndStationChange,
                onFindRoute = {
                    viewModel.findRoute()
                    navController.navigate("details_screen")
                },
                onSettingsClick = {
                    navController.navigate("settings_screen")
                }
            )
        }

        composable("details_screen") {
            val state by viewModel.screenState.collectAsState()
            RouteDetailsScreen(
                state = state,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("settings_screen") {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

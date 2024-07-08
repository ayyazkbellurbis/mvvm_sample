package com.sample.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.presentation.screen.NextScreen
import com.sample.presentation.screen.SampleScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoutes.SAMPLE_SCREEN) {
        composable(NavigationRoutes.SAMPLE_SCREEN) {
            SampleScreen(navController)
        }
        composable(NavigationRoutes.NEXT_SCREEN) {
            NextScreen(navController)
        }
    }
}
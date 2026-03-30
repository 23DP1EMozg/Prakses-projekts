package com.spuldz.praksesprojekts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spuldz.praksesprojekts.ui.screens.home.HomeScreen
import com.spuldz.praksesprojekts.ui.screens.start.StartScreen
import kotlinx.serialization.Serializable

@Serializable
object Start
@Serializable
object Home

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Start) {
        composable<Start> { StartScreen( onNavigateToHomeScreen = {
            navController.navigate(route = Home)
        } ) }
        composable<Home> { HomeScreen() }
    }
}

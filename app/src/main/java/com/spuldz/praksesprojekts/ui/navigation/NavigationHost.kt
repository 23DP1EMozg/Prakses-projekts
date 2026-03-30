package com.spuldz.praksesprojekts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spuldz.praksesprojekts.ui.screens.StartScreen
import kotlinx.serialization.Serializable

@Serializable
object Start

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Start) {
        composable<Start> { StartScreen() }
    }
}

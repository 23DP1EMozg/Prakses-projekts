package com.spuldz.praksesprojekts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.spuldz.praksesprojekts.ui.screens.game.GameScreen
import com.spuldz.praksesprojekts.ui.screens.home.HomeScreen
import com.spuldz.praksesprojekts.ui.screens.settings.SettingsScreen
import com.spuldz.praksesprojekts.ui.screens.start.StartScreen
import kotlinx.serialization.Serializable

@Serializable
object Start
@Serializable
object Home
@Serializable
data class Game(val difficulty: String)
@Serializable
object Settings

@Composable
fun NavigationHost() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Start) {
        composable<Start> {
            StartScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(route = Home)
                }
            )
        }
        composable<Home> {
            HomeScreen(
                onNavigateToGameScreen = { difficulty ->
                    navController.navigate(route = Game(difficulty))
                },
                onNavigateToSettingsScreen = {
                    navController.navigate(route = Settings)
                }
            )
        }

        composable<Settings> {
            SettingsScreen()
        }

        composable<Game> { backStackEntry ->
            val game = backStackEntry.toRoute<Game>()
            GameScreen(
                difficulty = game.difficulty,
                onNavigateHome = {
                    navController.navigate(route = Home)
                },
                onPlayAgain = {
                    navController.navigate(route = Game(game.difficulty))
                }
            )
        }
    }
}

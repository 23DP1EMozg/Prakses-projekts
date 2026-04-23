package com.spuldz.praksesprojekts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.spuldz.praksesprojekts.ui.screens.auth.LoginScreen
import com.spuldz.praksesprojekts.ui.screens.auth.RegisterScreen
import com.spuldz.praksesprojekts.ui.screens.game.GameScreen
import com.spuldz.praksesprojekts.ui.screens.home.HomeScreen
import com.spuldz.praksesprojekts.ui.screens.profile.ProfileScreen
import com.spuldz.praksesprojekts.ui.screens.scores.ScoresScreen
import com.spuldz.praksesprojekts.ui.screens.settings.AppearanceSettingsScreen
import com.spuldz.praksesprojekts.ui.screens.settings.ControlsSettingsScreen
import com.spuldz.praksesprojekts.ui.screens.settings.GameplaySettingsScreen
import com.spuldz.praksesprojekts.ui.screens.settings.LanguageSettingsScreen
import com.spuldz.praksesprojekts.ui.screens.settings.SettingsScreen
import com.spuldz.praksesprojekts.ui.screens.start.StartScreen
import kotlinx.serialization.Serializable

@Serializable
object Start
@Serializable
object Home
@Serializable
data class Game(val difficulty: String, val loadedGame: Boolean)
@Serializable
object Settings
@Serializable
object AppearanceSettings
@Serializable
object GameplaySettings
@Serializable
object ControlsSettings
@Serializable
object LanguageSettings
@Serializable
object Scores
@Serializable
object Profile
@Serializable
object Register
@Serializable
object Login

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Start) {
        composable<Start> {
            StartScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(route = Home)
                },
                onNavigateToLoginScreen = {
                    navController.navigate(route = Login)
                }
            )
        }
        composable<Home> {
            HomeScreen(
                onNavigateToGameScreen = { difficulty, loadedGame ->
                    navController.navigate(route = Game(difficulty, loadedGame))
                },
                onNavigateToSettingsScreen = {
                    navController.navigate(route = Settings)
                },
                onNavigateToScoresScreen = {
                    navController.navigate(route = Scores)
                },
                onNavigateToProfileScreen = {
                    navController.navigate(route = Profile)
                }
            )
        }

        composable<Scores> {
            ScoresScreen()
        }

        // Settings screens
        composable<Settings> {
            SettingsScreen(
                onAppearanceClick = {
                    navController.navigate(route = AppearanceSettings)
                },
                onGameplayClick = {
                    navController.navigate(route = GameplaySettings)
                },
                onControlsClick = {
                    navController.navigate(route = ControlsSettings)
                },
                onLanguageClick = {
                    navController.navigate(route = LanguageSettings)
                }
            )
        }
        composable<AppearanceSettings> {
            AppearanceSettingsScreen()
        }
        composable<GameplaySettings>{
            GameplaySettingsScreen()
        }
        composable<ControlsSettings> {
            ControlsSettingsScreen()
        }
        composable<LanguageSettings> {
            LanguageSettingsScreen()
        }

        composable<Profile> {
            ProfileScreen()
        }
        composable<Register> {
            RegisterScreen(
                onNavigateToLoginScreen = {
                    navController.navigate(route = Login)
                },
                onRegister = {
                    navController.navigate(route = Home)
                }
            )
        }

        composable<Login> {
            LoginScreen(
                onNavigateToRegisterScreen = {
                    navController.navigate(route = Register)
                },
                onLogin = {
                    navController.navigate(route = Home)
                }
            )
        }
        composable<Game> { backStackEntry ->
            val game = backStackEntry.toRoute<Game>()
            GameScreen(
                difficulty = game.difficulty,
                loadedGame = game.loadedGame,
                onNavigateHome = {
                    navController.navigate(route = Home)
                },
                onPlayAgain = {
                    navController.navigate(route = Game(game.difficulty, game.loadedGame))
                }
            )
        }
    }
}

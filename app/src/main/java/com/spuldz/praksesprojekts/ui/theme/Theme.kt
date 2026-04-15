package com.spuldz.praksesprojekts.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Theme(
    val name: String,
    val Primary: Color,
    val Secondary: Color,
    val Text: Color,
    val Background: Color,
    val PlacedCellText: Color,
    val HighlightColor: Color,
    val Error: Color,
    val BackgroundLighter: Color
)

val themes = listOf(
    DefaultTheme,
    MidnightNeonTheme,
    ArcticFrostTheme,
    CyberPunkTheme,
    EmeraldForestTheme
)

private val _sudokuTheme = MutableStateFlow(themes[0])
val sudokuTheme = _sudokuTheme.asStateFlow()
val LocalTheme = staticCompositionLocalOf { DefaultTheme }

fun setTheme(themeId: Int) {
    val theme = themes[themeId]
    _sudokuTheme.update { theme }
}

@Composable
fun PraksesProjektsTheme(
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val theme = sudokuTheme.collectAsState().value

    val colorScheme = lightColorScheme(
        primary = theme.Primary,
        secondary = theme.Secondary,
        background = theme.Background,
        onBackground = theme.Text
    )
    CompositionLocalProvider(
        LocalTheme provides theme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }

}

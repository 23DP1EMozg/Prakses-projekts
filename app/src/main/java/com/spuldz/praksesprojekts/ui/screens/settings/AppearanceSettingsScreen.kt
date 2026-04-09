package com.spuldz.praksesprojekts.ui.screens.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.setTheme
import com.spuldz.praksesprojekts.ui.theme.sizing
import com.spuldz.praksesprojekts.ui.theme.themes

@Composable
fun AppearanceSettingsScreen() {
    val theme = LocalTheme.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.Background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp30),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Appearance",
                style = TextLg,
                color = theme.Text
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(sizing.dp10)
        ) {
            themes.mapIndexed { index, theme ->
                ColorThemeOption(
                    text = theme.name,
                    onClick = { setTheme(index) },
                    color1 = theme.Primary,
                    color2 = theme.Secondary,
                    color3 = theme.Background
                )
            }
        }
    }
}

@Composable
fun ColorThemeOption(
    text: String,
    onClick: () -> Unit,
    color1: Color,
    color2: Color,
    color3: Color
) {
    val theme = LocalTheme.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(sizing.dp64)
            .background(theme.BackgroundLighter)
            .padding(horizontal = sizing.dp10)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center
    ) {
      Row(
          modifier = Modifier
              .padding(horizontal = sizing.dp10)
              .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
      ) {
          Text(
              text = text,
              style = TextMd,
              color = theme.Text
          )
          ThemePreviewCircle(color1, color2, color3)
      }
    }
}

@Composable
fun ThemePreviewCircle(
    color1: Color,
    color2: Color,
    color3: Color
) {
    Canvas(
        modifier = Modifier.size(sizing.dp30)
    ) {
        val startAngle = 0f
        val sweep = 120f

        drawArc(
            color = color1,
            startAngle = startAngle,
            sweepAngle = sweep,
            useCenter = true
        )

        drawArc(
            color = color2,
            startAngle = startAngle + sweep,
            sweepAngle = sweep,
            useCenter = true
        )

        drawArc(
            color = color3,
            startAngle = startAngle + 2 * sweep,
            sweepAngle = sweep,
            useCenter = true
        )
    }
}
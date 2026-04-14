package com.spuldz.praksesprojekts.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun SettingsScreen(
    onAppearanceClick: () -> Unit,
    onGameplayClick: () -> Unit,
    onControlsClick: () -> Unit,
    onLanguageClick: () -> Unit
) {
    val theme = LocalTheme.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.Background)
            .padding(top = sizing.dp30)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.settings),
                style = TextLg,
                color = theme.Text
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp30),
            verticalArrangement = Arrangement.spacedBy(sizing.dp10)
        ) {
            Category(stringResource(R.string.appearance), onAppearanceClick)
            Category(stringResource(R.string.gameplay), onGameplayClick)
            Category(stringResource(R.string.controls), onControlsClick)
            Category(stringResource(R.string.language), onLanguageClick)
        }
    }
}

@Composable
fun Category(
    text: String,
    onClick: () -> Unit
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
        Text(
            text = text,
            style = TextMd,
            color = theme.Text
        )
    }
}

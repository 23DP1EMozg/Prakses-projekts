package com.spuldz.praksesprojekts.ui.screens.settings

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.core.database.entities.Preferences
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.sizing
import java.util.Locale.getDefault

@Composable
fun ControlsSettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val theme = LocalTheme.current
    val prefs by viewModel.prefs.collectAsStateWithLifecycle()

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
                text = stringResource(R.string.controls),
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
            Row(
                modifier = Modifier.padding(horizontal = sizing.dp10)
            ) {
                Text(
                    text = stringResource(R.string.game_input_layout),
                    style = TextMd,
                    color = theme.Text
                )
            }
            ControlOption(
                text = stringResource(R.string.row),
                onClick = { viewModel.setGameInputLayout("row") },
                prefs = prefs,
                name = "row"
            )
            ControlOption(
                text = stringResource(R.string.grid),
                onClick = { viewModel.setGameInputLayout("grid") },
                name = "grid",
                prefs = prefs
            )
            ControlOption(
                text = stringResource(R.string.grid_center),
                name = "grid_center",
                onClick = { viewModel.setGameInputLayout("grid_center") },
                prefs = prefs
            )
        }
    }
}

@Composable
fun ControlOption(
    text: String,
    onClick: () -> Unit,
    prefs: Preferences?,
    name: String
) {
    val theme = LocalTheme.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(sizing.dp64)
            .background(theme.BackgroundLighter)
            .padding(horizontal = sizing.dp10)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            style = TextMd,
            color = theme.Text,
            text = text
        )
        if (prefs?.inputLayout == name.lowercase(getDefault())) {
            Image(
                modifier = Modifier
                    .height(sizing.dp30),
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.checkmark),
                contentDescription = null,
                colorFilter = ColorFilter.tint(theme.Primary)
            )
        }
    }
}

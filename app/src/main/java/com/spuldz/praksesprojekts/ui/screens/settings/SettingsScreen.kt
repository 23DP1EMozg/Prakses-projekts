package com.spuldz.praksesprojekts.ui.screens.settings

import androidx.compose.foundation.background
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
import com.spuldz.praksesprojekts.ui.theme.BackgroundColor
import com.spuldz.praksesprojekts.ui.theme.BackgroundLighterColor
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(top = sizing.dp30)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.settings),
                style = TextLg
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp30),
            verticalArrangement = Arrangement.spacedBy(sizing.dp10)
        ) {
            Category(stringResource(R.string.appearance))
            Category(stringResource(R.string.gameplay))
            Category(stringResource(R.string.controls))
            Category(stringResource(R.string.language))
        }
    }
}

@Composable
fun Category(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(sizing.dp64)
            .background(BackgroundLighterColor)
            .padding(horizontal = sizing.dp10),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = TextMd
        )
    }
}

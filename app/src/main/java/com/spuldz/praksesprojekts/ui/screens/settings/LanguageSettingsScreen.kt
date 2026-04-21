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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun LanguageSettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val theme = LocalTheme.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.Background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.language),
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
            LanguageOption(stringResource(
                R.string.english),
                R.drawable.uk_flag_icon,
                { viewModel.setLanguage(context, "en") }
            )
            LanguageOption(stringResource(
                R.string.latvian),
                R.drawable.latvia_flag_icon,
                { viewModel.setLanguage(context, "lv") }
            )
        }
    }
}

@Composable
private fun LanguageOption(
    language: String,
    image: Int,
    onClick: () -> Unit
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
            text = language
        )
        Image(
            modifier = Modifier
                .height(sizing.dp30),
            contentScale = ContentScale.Crop,
            painter = painterResource(image),
            contentDescription = null,
        )
    }
}

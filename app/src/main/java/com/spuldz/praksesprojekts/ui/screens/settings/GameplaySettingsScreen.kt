package com.spuldz.praksesprojekts.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun GameplaySettingsScreen(
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
                text = stringResource(R.string.gameplay),
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
            Option(
                text = "Hints",
                value = prefs.hintCount.toString(),
                onChange = { value -> viewModel.setHintCount(value) },
                label = "",
                placeholder = "hints"
            )
            Option(
                text = "Mistake Limit",
                value = prefs.mistakeLimit.toString(),
                onChange = { value -> viewModel.setMistakeLimit(value) },
                label = "",
                placeholder = "mistake limit"
            )
        }
    }
}

@Composable
fun Option(
    text: String,
    value: String,
    onChange: (String) -> Unit,
    label: String,
    placeholder: String
) {
    val theme = LocalTheme.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(sizing.dp64)
            .background(theme.BackgroundLighter)
            .padding(horizontal = sizing.dp10),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = TextMd,
            color = theme.Text
        )

        OutlinedTextField(
            modifier = Modifier
                .width(sizing.dp64),
            value = value,
            onValueChange = onChange,
            label = { Text(text = label) },
            placeholder = { Text(placeholder) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = theme.Text,
                unfocusedTextColor = theme.Text,
                focusedLabelColor = theme.Text,
                unfocusedLabelColor = theme.Text,
                focusedBorderColor = theme.Text,
                unfocusedBorderColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )
    }
}

package com.spuldz.praksesprojekts.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextMd

@Composable
fun Input(
    label: String,
    placeholder: String,
    value: String?,
    onChange: (String) -> Unit
) {
    val theme = LocalTheme.current

    Column() {
        Text(
            text = label,
            style = TextMd,
            color = theme.Text
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.7f),
            label = { Text(text = "") },
            placeholder = { Text(placeholder) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = theme.Text,
                unfocusedTextColor = theme.Text,
                focusedLabelColor = theme.Text,
                unfocusedLabelColor = theme.Text,
                focusedBorderColor = theme.Text,
                unfocusedBorderColor = Color.Gray
            ),
            singleLine = true,
            value = value.toString(),
            onValueChange = { value -> onChange(value) }
        )
    }
}

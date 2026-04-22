package com.spuldz.praksesprojekts.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun ProfileScreen() {
    val theme = LocalTheme.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = sizing.dp10)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp30),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Profile",
                style = TextLg,
                color = theme.Text
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp60),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Username",
                style = TextLg,
                color = theme.Text
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp30),
            verticalArrangement = Arrangement.spacedBy(sizing.dp20)
        ) {
            Input(
                "Change Username",
                "Username"
            )

            Input(
                "Change Password",
                "Password"
            )
        }
    }
}

@Composable
fun Input(
    label: String,
    placeholder: String,
) {
    val theme = LocalTheme.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            value = "",
            onValueChange = { }
        )
    }
}

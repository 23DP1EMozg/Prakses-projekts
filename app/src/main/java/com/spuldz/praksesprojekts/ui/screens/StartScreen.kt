package com.spuldz.praksesprojekts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.ui.theme.BackgroundColor
import com.spuldz.praksesprojekts.ui.theme.PrimaryColor
import com.spuldz.praksesprojekts.ui.theme.TitleStyle
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun StartScreen() {
    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .width(sizing.dp100)
                .height(sizing.dp100),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.start_screen_icon),
            contentDescription = null,
        )
        Text(
            text = "Welcome to sudoku!",
            style = TitleStyle

        )
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(top = sizing.dp20),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                contentColor = Color.White
            ),
            onClick = {}
        ) {
            Text("Start")
        }
    }

}
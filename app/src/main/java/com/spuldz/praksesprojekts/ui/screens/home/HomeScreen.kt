package com.spuldz.praksesprojekts.ui.screens.home
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.spuldz.praksesprojekts.ui.theme.BackgroundColor
import com.spuldz.praksesprojekts.ui.theme.HomeTitle
import com.spuldz.praksesprojekts.ui.theme.PraksesProjektsTheme
import com.spuldz.praksesprojekts.ui.theme.PrimaryColor
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(top = sizing.dp30),
            text = "Sudoku",
            style = HomeTitle,
            color = Color.White
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp30),
            horizontalArrangement = Arrangement.spacedBy(sizing.dp16, Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .width(sizing.dp150)
                    .height(sizing.dp150)
                    .background(Color.White)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(com.spuldz.praksesprojekts.R.drawable.settings_icon),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Box(
                    modifier = Modifier
                        .zIndex(3f)
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f)),
                ){
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "Settings",
                        color = Color.White,
                        style = TextMd
                    )
                }
            }
            Box(
                modifier = Modifier
                    .width(sizing.dp150)
                    .height(sizing.dp150)
                    .background(Color.White)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(com.spuldz.praksesprojekts.R.drawable.best_scores_icon),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Box(
                    modifier = Modifier
                        .zIndex(3f)
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f)),
                    ){
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "Scores",
                        color = Color.White,
                        style = TextMd
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp16),
            horizontalArrangement = Arrangement.spacedBy(sizing.dp16, Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .width(sizing.dp150)
                    .height(sizing.dp150)
                    .background(Color.White)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(com.spuldz.praksesprojekts.R.drawable.profile_icon),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Box(
                    modifier = Modifier
                        .zIndex(3f)
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f)),
                    ){
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "Profile",
                        color = Color.White,
                        style = TextMd
                    )
                }
            }

            Box(
                modifier = Modifier
                    .width(sizing.dp150)
                    .height(sizing.dp150)
                    .background(Color.White)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(com.spuldz.praksesprojekts.R.drawable.stats_icon),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Box(
                    modifier = Modifier
                        .zIndex(3f)
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f)),
                    ){
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "Stats",
                        color = Color.White,
                        style = TextMd
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Button(
            modifier = Modifier
                .padding(bottom = sizing.dp18)
                .fillMaxWidth(0.6f)
                .height(sizing.dp40),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                contentColor = Color.White
            ),
            onClick = {}
        ) {
            Text("New Game")
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    PraksesProjektsTheme {
        HomeScreen()
    }
}

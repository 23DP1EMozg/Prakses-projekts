package com.spuldz.praksesprojekts.ui.screens.home
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.ui.theme.HomeTitle
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.White
import com.spuldz.praksesprojekts.ui.theme.sizing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToGameScreen: (difficulty: String) -> Unit,
    onNavigateToSettingsScreen: () -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val theme = LocalTheme.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = sizing.dp30),
            text = stringResource(R.string.sudoku),
            style = HomeTitle,
            color = theme.Text
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp30),
            horizontalArrangement = Arrangement.spacedBy(sizing.dp16, Alignment.CenterHorizontally)
        ) {
            NavigationOption(stringResource(R.string.settings), R.drawable.settings_icon, { onNavigateToSettingsScreen() })
            NavigationOption(stringResource(R.string.scores), R.drawable.best_scores_icon, {})
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp16),
            horizontalArrangement = Arrangement.spacedBy(sizing.dp16, Alignment.CenterHorizontally)
        ) {

            NavigationOption(stringResource(R.string.profile), R.drawable.profile_icon, {})
            NavigationOption(stringResource(R.string.stats), R.drawable.stats_icon, {})
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
                containerColor = theme.Primary,
                contentColor = theme.Text
            ),
            onClick = { showBottomSheet = true }
        ) {
            Text(stringResource( R.string.new_game))
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = theme.Secondary,
            modifier = Modifier
                .padding(top = sizing.dp18)
                .padding(bottom = sizing.dp18)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(theme.Secondary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(sizing.dp10)
            ) {
                Text(
                    text = stringResource(R.string.choose_difficulty),
                    style = TextMd,
                    color = theme.Text
                )
                Spacer(
                    modifier = Modifier
                        .height(sizing.dp18)
                )
                DifficultyButton("Easy", { onNavigateToGameScreen("Easy") })
                DifficultyButton("Medium", { onNavigateToGameScreen("Medium") })
                DifficultyButton("Hard", { onNavigateToGameScreen("Hard") })
            }
        }
    }
}

@Composable
fun DifficultyButton(text: String, onNavigateToGameScreen: (String) -> Unit) {
    val theme = LocalTheme.current

    Button(
        onClick = { onNavigateToGameScreen(text) },
        colors = ButtonDefaults.buttonColors(
            containerColor = theme.Primary,
            contentColor = theme.Text
        ),
        modifier = Modifier
            .fillMaxWidth(0.7f))
    {
        Text(text)
    }
}
@Composable
fun NavigationOption(
    text: String,
    image: Int,
    onClick: () -> Unit
) {
    val theme = LocalTheme.current
    Box(
        modifier = Modifier
            .width(sizing.dp150)
            .height(sizing.dp150)
            .clickable { onClick() }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(image),
            contentScale = ContentScale.Fit,
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .zIndex(3f)
                .fillMaxSize()
                .background(theme.Background.copy(alpha = 0.7f)),
        ){
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = text,
                color = White,
                style = TextLg
            )
        }
    }
}

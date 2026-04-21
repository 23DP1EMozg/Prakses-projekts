package com.spuldz.praksesprojekts.ui.screens.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.core.models.GameModel
import com.spuldz.praksesprojekts.ui.theme.PrimaryColor
import com.spuldz.praksesprojekts.ui.theme.SecondaryColor
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextSm
import com.spuldz.praksesprojekts.ui.theme.White
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun EndGamePopup(
    game: GameModel?,
    onNavigateHome: () -> Unit,
    onPlayAgain: () -> Unit
) {
    AnimatedVisibility(
        visible = game?.isFinished == true,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(sizing.dp250)
                    .clip(RoundedCornerShape(sizing.dp10))
                    .background(PrimaryColor),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (game?.isWin == true) stringResource(R.string.you_win) else stringResource(R.string.you_lose),
                        style = TextLg
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = sizing.dp30),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.width(sizing.dp150),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryColor,
                            contentColor = White,
                        ),
                        onClick = { onNavigateHome() }
                    ) {
                        Text(
                            text = stringResource(R.string.return_home),
                            style = TextSm
                        )
                    }

                    Button(
                        modifier = Modifier.width(sizing.dp150),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryColor,
                            contentColor = White
                        ),
                        onClick = { onPlayAgain() }
                    ) {
                        Text(
                            text = stringResource(R.string.play_again),
                            style = TextSm
                        )
                    }
                }
            }
        }
    }
}

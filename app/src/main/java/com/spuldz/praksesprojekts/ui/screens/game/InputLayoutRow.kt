package com.spuldz.praksesprojekts.ui.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.core.models.GameInputModel
import com.spuldz.praksesprojekts.core.models.GameModel
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun InputLayoutRow(
    inputs: List<GameInputModel>?,
    onNumberClick: (Int) -> Unit,
    onTogglePencilMode: () -> Unit,
    game: GameModel?,
    onHint: () -> Unit
) {
    val theme = LocalTheme.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = sizing.dp10),
        horizontalArrangement = Arrangement.spacedBy(sizing.dp4, Alignment.CenterHorizontally)
    ) {
        if (inputs != null) {
            for (input in inputs) {
                Box(
                    modifier = Modifier
                        .height(sizing.dp70)
                        .width(sizing.dp38)
                        .background(theme.Secondary.copy(alpha = if (input.isComplete) {0.3f} else {1f}))
                        .border(sizing.dp2, theme.Primary.copy(alpha = if (input.isComplete) {0.3f} else {1f}))
                        .clickable { if (!input.isComplete) {onNumberClick(input.value)} }
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = input.value.toString(),
                        color = theme.Text.copy(alpha = if (input.isComplete) {0.3f} else {1f}),
                        style = TextLg
                    )
                }
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = sizing.dp20),
        horizontalArrangement = Arrangement.spacedBy(sizing.dp14, Alignment.End)
    ) {
        Tool(
            onClick = onTogglePencilMode,
            name = stringResource(R.string.pencil),
            image = R.drawable.pencil_icon,
            condition = game?.pencilMode == true,
            game = game,
        )
        Tool(
            onClick = onHint,
            name = stringResource(R.string.hint),
            image = R.drawable.hint_icon,
            condition = game?.hintMode == true,
            game = game,
            amount = 3
        )
    }
}

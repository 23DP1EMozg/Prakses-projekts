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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
fun InputLayoutGridCenter(
    inputs: List<GameInputModel>?,
    onNumberClick: (Int) -> Unit,
    onTogglePencilMode: () -> Unit,
    game: GameModel?,
    onHint: () -> Unit
) {
    val theme = LocalTheme.current
    Row(
        modifier = Modifier
            .padding(top = sizing.dp10)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Tool(
            onClick = onHint,
            name = stringResource(R.string.hint),
            image = R.drawable.hint_icon,
            condition = game?.hintMode == true,
            game = game,
            amount = 3
        )
        LazyVerticalGrid(
            modifier = Modifier
                .width(sizing.dp50 * 3)
                .height(sizing.dp50 * 3),
            columns = GridCells.Fixed(3)
        ) {
            if (inputs != null) {
                items(inputs.size) { index ->
                    val input = inputs[index]
                    Box(
                        modifier = Modifier
                            .height(sizing.dp50)
                            .width(sizing.dp50)
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
        Tool(
            onClick = onTogglePencilMode,
            name = stringResource(R.string.pencil),
            image = R.drawable.pencil_icon,
            condition = game?.pencilMode == true,
            game = game,
        )
    }
}

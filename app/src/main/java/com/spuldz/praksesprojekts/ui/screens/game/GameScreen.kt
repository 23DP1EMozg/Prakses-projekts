package com.spuldz.praksesprojekts.ui.screens.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.core.database.entities.Preferences
import com.spuldz.praksesprojekts.core.models.GameInputModel
import com.spuldz.praksesprojekts.core.models.GameModel
import com.spuldz.praksesprojekts.core.models.GridCellModel
import com.spuldz.praksesprojekts.ui.theme.Black
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextPencil
import com.spuldz.praksesprojekts.ui.theme.TextSm
import com.spuldz.praksesprojekts.ui.theme.sizing
import timber.log.Timber

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    difficulty: String,
    onNavigateHome: () -> Unit,
    onPlayAgain: () -> Unit
) {
    val grid by viewModel.gameBoard.collectAsStateWithLifecycle()
    val game by viewModel.game.collectAsStateWithLifecycle()
    val inputs by viewModel.inputs.collectAsStateWithLifecycle()
    val preferences by viewModel.preferences.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Timber.d(difficulty)
        viewModel.generateGameBoard(difficulty)
        viewModel.updateInputLayout()
        Timber.d("GAME SCREEN LAUNCH EFFECT: $preferences")
    }
    GameScreenContent(
        grid = grid,
        game = game,
        inputs = inputs,
        onCellClick = viewModel::selectCell,
        onNumberClick = viewModel::addNumberToSelectedCell,
        onTogglePencilMode = viewModel::togglePencilMode,
        onHint = viewModel::onHint,
        getPencilGridRows = viewModel::getPencilGridRows,
        onNavigateHome = onNavigateHome,
        onPlayAgain = onPlayAgain,
        preferences = preferences
    )
}

@Composable
private fun GameScreenLoading() {
    val theme = LocalTheme.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.Background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(sizing.dp64),
            color = Color.White,
            strokeWidth = 6.dp
        )
    }
}

@Composable
fun GameScreenContent(
    grid: List<List<GridCellModel>>?,
    game: GameModel?,
    inputs: List<GameInputModel>?,
    onCellClick: (GridCellModel) -> Unit,
    onNumberClick: (Int) -> Unit,
    onTogglePencilMode: () -> Unit,
    onHint: () -> Unit,
    getPencilGridRows: (GridCellModel) -> MutableList<MutableList<String>>,
    onNavigateHome: () -> Unit,
    onPlayAgain: () -> Unit,
    preferences: Preferences
) {
    if(grid == null){
        GameScreenLoading()
    }else{
        GameScreenGrid(
            grid = grid,
            game = game,
            inputs = inputs,
            onCellClick = onCellClick,
            onNumberClick = onNumberClick,
            onTogglePencilMode = onTogglePencilMode,
            onHint = onHint,
            getPencilGridRows = getPencilGridRows,
            preferences = preferences
        )
        EndGamePopup(
            game = game,
            onNavigateHome = onNavigateHome,
            onPlayAgain = onPlayAgain
        )
    }
}

@Composable
private fun GridCell(
    cell: GridCellModel,
    onCellClick: (GridCellModel) -> Unit,
    getPencilGridRows: (GridCellModel) -> MutableList<MutableList<String>>
) {
    val theme = LocalTheme.current
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (cell.isLightUp || cell.isSelected && !cell.isError) theme.Secondary
            else if (cell.isError) theme.Error
            else if (cell.isHighlighted) theme.HighlightColor
            else theme.Primary,
        animationSpec = tween(
            easing = EaseOut
        )
    )

    if (cell.isError) {
        Timber.d("ERROR CELL: $cell")
    }
    Box(
        modifier = Modifier
            .width(sizing.dp40)
            .height(sizing.dp40)
            .background(animatedBackgroundColor)
            .border(sizing.dp2, if (cell.isSelected) theme.PlacedCellText else Color.Transparent)
            .clickable { onCellClick(cell) }
            .drawBehind {

                val strokeWidth = if (
                    (cell.colNumber + 1) % 3 == 0 || cell.colNumber == 0
                ) 2.dp.toPx() else 0.5.dp.toPx()

                val xPos = if (cell.colNumber != 0) size.width else 0f
                drawLine(
                    color = Color.Black,
                    start = Offset(xPos, 0f),
                    end = Offset(xPos, size.width),
                    strokeWidth = strokeWidth
                )

                if(cell.colNumber == 0) {
                    drawLine(
                        color = Color.Black,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.width),
                        strokeWidth = 0.5.dp.toPx()
                    )
                }
            }
    ) {
        if (cell.pencilValue != null && cell.value == 0) {
            val rows = getPencilGridRows(cell)
            val row1 = rows[0]
            val row2 = rows[1]
            val row3 = rows[2]
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                 verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1 / 3f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row1.forEach { num  ->
                        Text(
                            text = num,
                            style = TextPencil,
                            color = theme.Text
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1 / 3f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row2.forEach { num  ->
                        Text(
                            text = num,
                            style = TextPencil,
                            color = theme.Text
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1 / 3f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row3.forEach { num  ->
                        Text(
                            text = num,
                            style = TextPencil,
                            color = theme.Text
                        )
                    }
                }
            }
        }else {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = if (cell.value == 0) ""
                else cell.value.toString(),
                color = if (cell.isPlayerPlaced) theme.PlacedCellText else theme.Text,
                style = if (cell.value == 0 && cell.pencilValue != null) TextPencil else TextLg
            )
        }
    }
}

@Composable
private fun GameScreenGrid(
    grid: List<List<GridCellModel>>,
    game: GameModel?,
    inputs: List<GameInputModel>?,
    onCellClick: (GridCellModel) -> Unit,
    onNumberClick: (Int) -> Unit,
    onTogglePencilMode: () -> Unit,
    onHint: () -> Unit,
    getPencilGridRows: (GridCellModel) -> MutableList<MutableList<String>>,
    preferences: Preferences
) {
    val theme = LocalTheme.current
    val difficulty = when (game?.difficulty) {
        "Easy" -> stringResource(R.string.easy)
        "Medium" -> stringResource(R.string.medium)
        "Hard" -> stringResource(R.string.medium)
        else -> stringResource(R.string.medium)
    }

    Column(
        modifier = Modifier
            .background(theme.Background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.mistakes) + ": ${game?.mistakes}/${game?.mistakeLimit}",
                style = TextSm,
                color = theme.Text
            )
            Text(
                text = stringResource(R.string.difficulty) + ": $difficulty",
                style = TextSm,
                color = theme.Text
            )
            Text(
                text = "${game?.time}",
                style = TextSm,
                color = theme.Text
            )
        }
        for ((rowIndex, row) in grid.withIndex()) {
            Row(
                modifier = Modifier
                    .height(sizing.dp40)
                    .drawBehind {
                        val strokeWidth = if ((rowIndex) % 3 == 0) 2.dp.toPx() else 0.5.dp.toPx()

                        drawLine(
                            color = Black,
                            start = Offset(size.width, 0f),
                            end = Offset(0f, 0f),
                            strokeWidth = strokeWidth
                        )

                        if (rowIndex == 8) {
                            drawLine(
                                color = Black,
                                start = Offset(size.width, size.height),
                                end = Offset(0f, size.height),
                                strokeWidth = 2.dp.toPx()
                            )
                        }

                    }
            ) {
                for (cell in row) {
                    GridCell(cell, onCellClick, getPencilGridRows)
                }
            }
        }

        when (preferences.inputLayout) {
            "row" ->
                InputLayoutRow(
                    inputs = inputs,
                    onNumberClick = onNumberClick,
                    onTogglePencilMode = onTogglePencilMode,
                    game = game,
                    onHint = onHint
                )
            "grid" ->
                InputLayoutGrid(
                    inputs = inputs,
                    onNumberClick = onNumberClick,
                    onTogglePencilMode = onTogglePencilMode,
                    game = game,
                    onHint = onHint
                )
            "grid_center" ->
                InputLayoutGridCenter(
                    inputs = inputs,
                    onNumberClick = onNumberClick,
                    onTogglePencilMode = onTogglePencilMode,
                    game = game,
                    onHint = onHint
                )
        }
    }
}

@Composable
fun Tool(
    onClick: () -> Unit,
    name: String,
    image: Int,
    condition: Boolean,
    amount: Int? = null,
    game: GameModel?
) {
    val theme = LocalTheme.current
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(sizing.dp50)
                .height(sizing.dp50),
            painter = painterResource(image),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            colorFilter = ColorFilter.tint(if (condition) theme.Primary else theme.Text)
        )
        Text(
            text = name,
            style = TextSm,
            color = theme.Text
        )
        if (amount != null) {
            Text(
                text = "${game?.hintsLeft}/${amount}",
                style = TextSm,
                color = theme.Text
            )
        }
    }
}

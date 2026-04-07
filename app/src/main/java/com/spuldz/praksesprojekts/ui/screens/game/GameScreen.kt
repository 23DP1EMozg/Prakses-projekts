package com.spuldz.praksesprojekts.ui.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.core.models.GridCellModel
import com.spuldz.praksesprojekts.ui.theme.BackgroundColor
import com.spuldz.praksesprojekts.ui.theme.PrimaryColor
import com.spuldz.praksesprojekts.ui.theme.SecondaryColor
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.TextSm
import com.spuldz.praksesprojekts.ui.theme.sizing
import timber.log.Timber

@Composable
fun GameScreen(viewModel: GameViewModel = hiltViewModel()) {
    val grid by viewModel.gameBoard.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Timber.d("create game grid")
        viewModel.generateGameBoard()
    }

    GameScreenContent(
        grid = grid,
        onCellClick = viewModel::selectCell,
        onNumberClick = viewModel::addNumberToSelectedCell,
    )
}

@Composable
private fun GameScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
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
    onCellClick: (GridCellModel) -> Unit,
    onNumberClick: (Int) -> Unit
) {
    if(grid == null){
        GameScreenLoading()
    }else{
        GameScreenGrid(grid, onCellClick, onNumberClick)
    }
}

@Composable
private fun GridCell(cell: GridCellModel, onCellClick: (GridCellModel) -> Unit) {
    val strokeWidthLarge = sizing.dp2
    val strokeWidthSmall = sizing.dp05
    Box(
        modifier = Modifier
            .width(sizing.dp40)
            .height(sizing.dp40)
            .background(
                if (cell.isSelected) SecondaryColor else PrimaryColor
            )
            .clickable { onCellClick(cell) }
            .drawBehind {

                val strokeWidth = if (
                    (cell.colNumber + 1) % 3 == 0 || cell.colNumber == 0
                ) strokeWidthLarge.toPx() else strokeWidthSmall.toPx()

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
                        strokeWidth = strokeWidthSmall.toPx()
                    )
                }
            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = if (cell.value == 0) "" else cell.value.toString(),
            color = Color.White,
            style = TextMd
        )
    }
}

@Composable
private fun GameScreenGrid(
    grid: List<List<GridCellModel>>,
    onCellClick: (GridCellModel) -> Unit,
    onNumberClick: (Int) -> Unit
){
    val strokeWidthLarge = sizing.dp2
    val strokeWidthSmall = sizing.dp05
    Column(
        modifier = Modifier
            .background(BackgroundColor)
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
                text = stringResource(R.string.mistakes) + ": 0/3",
                style = TextSm
            )
            Text(
                text = stringResource(R.string.difficulty) + ": normal",
                style = TextSm
            )
            Text(
                text = "00:00",
                style = TextSm
            )

        }

        for((rowIndex, row) in grid.withIndex()) {
            Row(
                modifier = Modifier
                    .height(sizing.dp40)
                    .drawBehind {
                        val strokeWidth = if((rowIndex) % 3 == 0) strokeWidthLarge.toPx() else strokeWidthSmall.toPx()

                        drawLine(
                            color = Color.Black,
                            start = Offset(size.width, 0f),
                            end = Offset(0f, 0f),
                            strokeWidth = strokeWidth
                        )

                        if (rowIndex == 8){
                            drawLine(
                                color = Color.Black,
                                start = Offset(size.width, size.height),
                                end = Offset(0f, size.height),
                                strokeWidth = strokeWidthLarge.toPx()
                            )
                        }

                    }
            ) {
                for( cell in row) {
                    GridCell(cell, onCellClick)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp10),
            horizontalArrangement = Arrangement.spacedBy(sizing.dp4, Alignment.CenterHorizontally)
        ) {
            for (num in 1..9) {
                Box(
                    modifier = Modifier
                        .height(sizing.dp70)
                        .width(sizing.dp38)
                        .background(SecondaryColor)
                        .border(sizing.dp2, PrimaryColor)
                        .clickable { onNumberClick(num) }
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = num.toString(),
                        color = Color.White,
                        style = TextMd
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.spacedBy(sizing.dp14, Alignment.End)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = sizing.dp20)
                        .width(sizing.dp50)
                        .height(sizing.dp50)
                    ,
                    painter = painterResource(R.drawable.pencil_icon),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Text(
                    text = "Pencil",
                    style = TextSm
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = sizing.dp20)
                        .width(sizing.dp50)
                        .height(sizing.dp50)
                    ,
                    painter = painterResource(R.drawable.eraser_icon),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Text(
                    text = "Eraser",
                    style = TextSm
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = sizing.dp20)
                        .width(sizing.dp50)
                        .height(sizing.dp50)
                    ,
                    painter = painterResource(R.drawable.hint_icon),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Text(
                    text = "Hint",
                    style = TextSm
                )
            }
        }
    }
}





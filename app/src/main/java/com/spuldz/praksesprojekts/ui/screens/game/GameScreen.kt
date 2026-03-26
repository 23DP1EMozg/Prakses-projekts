package com.spuldz.praksesprojekts.ui.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spuldz.praksesprojekts.core.models.GridCellModel
import com.spuldz.praksesprojekts.ui.common.mockSudokuBoard
import com.spuldz.praksesprojekts.ui.theme.BackgroundColor
import com.spuldz.praksesprojekts.ui.theme.PraksesProjektsTheme
import com.spuldz.praksesprojekts.ui.theme.PrimaryColor
import com.spuldz.praksesprojekts.ui.theme.SecondaryColor
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.TextSm
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun GameScreen(viewModel: GameViewModel = hiltViewModel()) {
    val grid by viewModel.gameBoard.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.generateGameBoard()
    }

    GameScreenContent(grid)
}

@Composable
private fun GameScreenLoading(){

}

@Composable
fun GameScreenContent(
    grid: List<List<GridCellModel>>?
) {
    if(grid == null){
        GameScreenLoading()
    }else{
        GameScreenGrid(grid)
    }
}

@Composable
private fun GameScreenGrid(
    grid: List<List<GridCellModel>>
){
    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(0.88f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mistakes: 0/3",
                style = TextSm
            )
            Text(
                text = "Difficulity: normal",
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
                        val strokeWidth = if((rowIndex) % 3 == 0) 2.dp.toPx() else 0.5.dp.toPx()

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
                                strokeWidth = 2.dp.toPx()
                            )
                        }

                    }
            ) {
                for((colIndex, cell) in row.withIndex()) {
                    Box(
                        modifier = Modifier
                            .width(sizing.dp40)
                            .height(sizing.dp40)
                            .background(PrimaryColor)
                            .drawBehind {

                                val strokeWidth = if (
                                    (colIndex + 1) % 3 == 0 || colIndex == 0
                                ) 2.dp.toPx() else 0.5.dp.toPx()

                                val xPos = if (colIndex != 0) size.width else 0f
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(xPos, 0f),
                                    end = Offset(xPos, size.width),
                                    strokeWidth = strokeWidth
                                )

                                if(colIndex == 0) {
                                    drawLine(
                                        color = Color.Black,
                                        start = Offset(size.width, 0f),
                                        end = Offset(size.width, size.width),
                                        strokeWidth = 0.5.dp.toPx()
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
            modifier = Modifier
                .fillMaxWidth(0.9f),
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
                    painter = painterResource(com.spuldz.praksesprojekts.R.drawable.pencil_icon),
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
                    painter = painterResource(com.spuldz.praksesprojekts.R.drawable.eraser_icon),
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
                    painter = painterResource(com.spuldz.praksesprojekts.R.drawable.hint_icon),
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



@Preview
@Composable
private fun GameScreenPreview() {
    PraksesProjektsTheme {
        GameScreenContent(null)
    }
}



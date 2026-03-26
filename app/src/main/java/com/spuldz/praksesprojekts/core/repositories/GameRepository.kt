package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.models.GridCellModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor() {

    private val _gameBoard = MutableStateFlow<List<List<GridCellModel>>?>(null)
    val gameBoard = _gameBoard.asStateFlow()

    fun generateBoilerplate(): MutableList<MutableList<GridCellModel>> {
        val board: MutableList<MutableList<GridCellModel>> = mutableListOf()

        for (row in 0..8) {
            val list = mutableListOf<GridCellModel>()
            for (col in 0..8) {
                list.add(
                    GridCellModel(
                        0,
                        true,
                        row,
                        col,
                        intArrayOf((row / 3) * 3, (col / 3) * 3)
                    )
                )
            }
            board.add(list)
        }

        return board
    }

    fun fillGameBoard(board: MutableList<MutableList<GridCellModel>>) : Boolean{
        for (row in 0..8) {
            for (col in 0..8) {
                if (board[row][col].value == 0) {
                    val nums = (1..9).shuffled()
                    for (num in nums) {
                        if(isValid(board, row, col, num)) {
                            board[row][col] = board[row][col]
                                .copy(
                                    value = num
                                )

                            if(fillGameBoard(board)) {
                                return true
                            }

                            board[row][col] = board[row][col].copy( value = 0 )
                        }
                    }
                    return false
                }
            }
        }
        _gameBoard.update { board.toList() }
        return true
    }

    private fun isValid(board: MutableList<MutableList<GridCellModel>>, row: Int, col: Int, num: Int) : Boolean {
        return !numberInRow(board[row], num) &&
                !numberInColumn(board, col, num) &&
                !numberInSquare(board[row][col], board, num)
    }


    private fun numberInRow(row: MutableList<GridCellModel>, number: Int): Boolean {
        for (cell in row) {
            if (cell.value == number) {
                return true
            }
        }
        return false
    }

    private fun numberInColumn(
        board: MutableList<MutableList<GridCellModel>>,
        col: Int,
        number: Int
    ): Boolean {
        for (row in board) {
            if (row[col].value == number) {
                return true
            }
        }
        return false
    }

    private fun numberInSquare(
        cell: GridCellModel,
        grid: MutableList<MutableList<GridCellModel>>,
        number: Int
    ): Boolean {
        val startRow: Int = cell.squareStart[0]
        val startCol: Int = cell.squareStart[1]


        for (row in startRow..startRow + 2) {
            for (col in startCol..startCol + 2) {
                if (grid[row][col].value == number) {
                    return true
                }
            }
        }
        return false
    }
}

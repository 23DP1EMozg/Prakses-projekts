package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.models.GridCellModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class GameRepository @Inject constructor() {

    private val _gameBoard = MutableStateFlow<List<List<GridCellModel>>?>(null)
    private var selectedCell: GridCellModel? = null
    val gameBoard = _gameBoard.asStateFlow()

    fun generateBoilerplate(): MutableList<MutableList<GridCellModel>> {
        val board: MutableList<MutableList<GridCellModel>> = mutableListOf()

        for (row in 0..8) {
            val list = mutableListOf<GridCellModel>()
            for (col in 0..8) {
                list.add(
                    GridCellModel(
                       value = 0,
                        isEditable = false,
                        rowNumber = row,
                        colNumber = col,
                        squareStart = Pair((row / 3) * 3, (col / 3) * 3)
                    )
                )
            }
            board.add(list)
        }
        return board
    }

    fun generateSolutionAndFillBoard(
        board: MutableList<MutableList<GridCellModel>>
    ): Boolean {
        for (row in 0..8) {
            for (col in 0..8) {
                if (board[row][col].value == 0) {
                    val nums = (1..9).shuffled()
                    for (num in nums) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = board[row][col]
                                .copy(
                                    value = num
                                )

                            if (generateSolutionAndFillBoard(board)) return true
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

    fun removeCellsFromBoard(
        board: MutableList<MutableList<GridCellModel>>,
        amount: Int
    ) : MutableList<MutableList<GridCellModel>> {
        var times = 0

        while (times <= amount) {
            val randomRow = Random.nextInt(0,9)
            val randomCol = Random.nextInt(0,9)

            if (board[randomRow][randomCol].value == 0) {
                continue
            }

            val prevValueCopy = board[randomRow][randomCol].value
            board[randomRow][randomCol] = board[randomRow][randomCol].copy(
                value = 0,
                isEditable = true
            )

            var count = 0
            for (num in 1..9) {
                if (isValid(board, randomRow, randomCol, num)) {
                    count++
                }
            }

            if (count > 1) {
                board[randomRow][randomCol] = board[randomRow][randomCol].copy(
                    value = prevValueCopy
                )
            } else {
                times++
            }
        }
        return board
    }

    suspend fun fillGameBoard(difficulty: String) {
        val board = generateBoilerplate()
        generateSolutionAndFillBoard(board)
        val amount = if (difficulty == "Easy") 30
                    else if (difficulty == "Normal") 50
                    else 70

        val boardWithRemovedCells = removeCellsFromBoard(board, amount)
        _gameBoard.update { boardWithRemovedCells.toList() }
    }

    private fun isValid(board: MutableList<MutableList<GridCellModel>>, row: Int, col: Int, num: Int) : Boolean {
        return !numberInRow(board[row], num) &&
                !numberInColumn(board, col, num) &&
                !numberInSquare(board[row][col], board, num)
    }

    private fun numberInRow(row: MutableList<GridCellModel>, number: Int) : Boolean {
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
    ) : Boolean {
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
        val startRow: Int = cell.squareStart.first
        val startCol: Int = cell.squareStart.second

        for (row in startRow..startRow + 2) {
            for (col in startCol..startCol + 2) {
                if (grid[row][col].value == number) {
                    return true
                }
            }
        }
        return false
    }

    fun selectCell(cell: GridCellModel) {
        if (!cell.isEditable) {
            return
        }

        var newBoard = _gameBoard.value?.map { it.toMutableList() }?.toMutableList()
        newBoard = newBoard?.map { it.map { it.copy(isSelected = false) }.toMutableList() }?.toMutableList()
        selectedCell = cell

        newBoard?.get(cell.rowNumber)[cell.colNumber] = cell.copy(isSelected = true)
        _gameBoard.update { newBoard }
    }

    fun addNumberToSelectedCell(number: Int) {
        val newBoard = _gameBoard.value?.map { it.toMutableList() }?.toMutableList()
        val selectedCellTemp = selectedCell ?: return
        if (newBoard == null) return
        if (!selectedCellTemp.isEditable) return

        if (isValid(newBoard, selectedCellTemp.rowNumber, selectedCellTemp.colNumber, number)) {
            newBoard.get(selectedCellTemp.rowNumber)[selectedCellTemp.colNumber] = selectedCellTemp.copy(
                value = number,
                isEditable = false
            )
        } else {
            Timber.d("WRONG!")
        }

        _gameBoard.update { newBoard }
    }
}

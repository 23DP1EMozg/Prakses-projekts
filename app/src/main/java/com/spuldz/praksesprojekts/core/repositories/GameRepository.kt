package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.models.GridCellModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor() {
    private val _gameBoard = MutableStateFlow<List<List<GridCellModel>>?>(null)
    private var solution: MutableList<MutableList<GridCellModel>>? = null
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

    fun getFilledBoard() : MutableList<MutableList<GridCellModel>>{
        val board = generateBoilerplate()
        fun generateSolutionAndFillBoard(
            board: MutableList<MutableList<GridCellModel>>
        ): Boolean {
            for (row in 0..8) {
                for (col in 0..8) {
                    if (board[row][col].value == 0) {
                        val numbers = (1..9).shuffled()
                        for (num in numbers) {
                            if(isValid(board, row, col, num)) {
                                board[row][col] = board[row][col]
                                    .copy(
                                        value = num
                                    )

                                if(generateSolutionAndFillBoard(board)) return true
                                board[row][col] = board[row][col].copy( value = 0 )
                            }
                        }
                        return false
                    }
                }
            }
            return true
        }
        generateSolutionAndFillBoard(board)
        return board
    }

    fun removeCellsFromBoard(board: MutableList<MutableList<GridCellModel>>, amount: Int) : MutableList<MutableList<GridCellModel>>{
        var removed = 0
        val allOccupiedCells = board.flatten().shuffled().filter { it.value != 0 }.toMutableList()
        val boardCopy = copyBoard(board)

        for (cell in allOccupiedCells) {
            if (removed >= amount) break

            boardCopy[cell.rowNumber][cell.colNumber] = boardCopy[cell.rowNumber][cell.colNumber].copy(
                value = 0,
                isEditable = true
            )
            var boardCopy1 = boardCopy.map { row ->
                row.map { col -> col.copy() }
            }
            boardCopy1 = boardCopy1.map { it.toMutableList() }.toMutableList()
            val solutions = getSolutionCount(boardCopy1)

            if (solutions > 1){
                boardCopy[cell.rowNumber][cell.colNumber] = boardCopy[cell.rowNumber][cell.colNumber].copy(
                    value = cell.value
                )
            }else{
                removed++
            }
        }
        return boardCopy
    }

    suspend fun fillGameBoard(difficulty: String){
        val board = getFilledBoard()
        solution = board
        val amount = when(difficulty){
            "Easy" -> 40
            "Medium" -> 50
            "Hard" -> 60
            else -> 50
        }
        for (row in board) {
            Timber.d(row.map { it.value }.toString())
        }
        val boardWithRemovedCells = removeCellsFromBoard(board, amount)
        _gameBoard.update { boardWithRemovedCells.map { it.toList() }.toList() }
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
        var newBoard = _gameBoard.value?.map { it.toMutableList() }?.toMutableList()
        newBoard = newBoard?.map { it.map { col -> col.copy(
            isSelected = false,
            isLightUp = false
        ) }.toMutableList() }?.toMutableList()

        newBoard?.get(cell.rowNumber)[cell.colNumber] = cell.copy(
            isSelected = true,
        )

        newBoard?.forEach { row ->
            row.forEach { c ->
                if (c.value == cell.value && cell.value != 0) newBoard[c.rowNumber][c.colNumber] =
                    newBoard[c.rowNumber][c.colNumber].copy(isLightUp = true)
            }
        }
        Timber.d(newBoard?.get(cell.rowNumber)[cell.colNumber].toString())
        _gameBoard.update { newBoard }
    }

    private fun copyBoard(board: MutableList<MutableList<GridCellModel>>) : MutableList<MutableList<GridCellModel>> {
        val copy = board.map { row ->
            row.map { cell -> cell.copy() }
        }

        return copy.map { it.toMutableList() }.toMutableList()
    }

        suspend fun addNumberToSelectedCell(number: Int) {
        val newBoard = _gameBoard.value?.map { it.toMutableList() }?.toMutableList()
            val selectedCell = newBoard
                ?.flatten()
                ?.firstOrNull {it.isSelected}

            if (newBoard != null) {
                for (row in newBoard) {
                    Timber.d(row.map { it.isSelected }.toString())
                }
            }

            if (selectedCell == null) return

            if (!selectedCell.isEditable) return

            val row = selectedCell.rowNumber
            val col = selectedCell.colNumber

            if (solution != null) {
                for (row in solution) {
                    Timber.d(row.map { it.value }.toString())
                }
            }

            if (solution?.get(row)[col]?.value == number) {
                newBoard[row][col] = selectedCell.copy(
                    value = number,
                    isPlayerPlaced = true,
                    isEditable = false,
                )
            }else{
                newBoard[row][col] = selectedCell.copy(
                    value = number,
                    isError = true,
                    isEditable = false
                )
                _gameBoard.update { copyBoard(newBoard) }
                delay(2000)
                newBoard[row][col] = selectedCell.copy(
                    value = 0,
                    isError = false,
                    isEditable = true,
                    isPlayerPlaced = false
                )
            }
           _gameBoard.update { copyBoard(newBoard) }
      }

    fun getSolutionCount(board: MutableList<MutableList<GridCellModel>>) : Int {
        var solutions = 0

        fun solve() : Boolean{
            for (row in 0..8) {
                for (col in 0..8) {
                    if(board[row][col].value == 0) {
                        for (num in 1..9) {
                            if(isValid(board, row, col, num)) {
                                board[row][col] = board[row][col].copy(
                                    value = num
                                )

                                if (solve()) return true

                                board[row][col] = board[row][col].copy(value = 0)
                            }
                        }
                        return false
                    }
                }
            }
            solutions++
            return solutions >= 2
        }
        solve()
        return solutions
    }
}

package com.spuldz.praksesprojekts.core.handlers

import com.spuldz.praksesprojekts.core.models.GridCellModel

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

fun isValid(board: MutableList<MutableList<GridCellModel>>, row: Int, col: Int, num: Int) : Boolean {
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
fun copyBoard(board: MutableList<MutableList<GridCellModel>>) : MutableList<MutableList<GridCellModel>> {
    return board.map { row ->
        row.map { cell -> cell.copy() }.toMutableList()
    }.toMutableList()
}

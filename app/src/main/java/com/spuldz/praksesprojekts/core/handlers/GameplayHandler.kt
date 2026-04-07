package com.spuldz.praksesprojekts.core.handlers

import com.spuldz.praksesprojekts.core.models.GameInputModel
import com.spuldz.praksesprojekts.core.models.GridCellModel

fun isNumberComplete(number: Int, board: MutableList<MutableList<GridCellModel>>) : Boolean {
    var count = 0
    board.forEach { row ->
        row.forEach { cell ->
            if (cell.value == number) count++
        }
    }

    return count == 9
}

fun isBoardComplete(board: MutableList<MutableList<GridCellModel>>): Boolean {
    val emptyCell = board.flatten().firstOrNull {it.value == 0}
    return emptyCell == null
}

fun updateGameInputs(number: Int, inputs: List<GameInputModel>?) : MutableList<GameInputModel>? {
        val inputsCopy = inputs?.map { it.copy() }?.toMutableList()

        inputsCopy?.forEach { inp ->
            if (inp.value == number) {
                inp.isComplete = true
                return@forEach
            }
        }
        return inputsCopy
}

fun enterPencilNumber(board: MutableList<MutableList<GridCellModel>>, number: Int, cell: GridCellModel): MutableList<MutableList<GridCellModel>> {
    var content = cell.pencilValue
    val contentList = content?.trim()?.split("")
    val boardCopy = copyBoard(board)

    if (
        contentList?.contains(number.toString()) == true ||
        !isValid(board, cell.rowNumber, cell.colNumber, number)
    ) {
        return boardCopy
    }

    content = "${content ?: ""}$number"
    boardCopy[cell.rowNumber][cell.colNumber] = cell.copy(
        pencilValue = content
    )

    return boardCopy
}

fun updatePencilEnteredNumbers(board: MutableList<MutableList<GridCellModel>>?) : MutableList<MutableList<GridCellModel>> {
    if (board == null) return mutableListOf()

    val boardCopy = copyBoard(board).toMutableList()
    val cells = boardCopy.flatten().filter { it.pencilValue != null && it.value == 0}

    cells.forEach { c ->
        val list = c.pencilValue?.trim()?.map { it.toString() }
        val filtered = list?.filter { isValid(boardCopy, c.rowNumber, c.colNumber, it.toInt()) }
        boardCopy[c.rowNumber][c.colNumber] = c.copy(
            pencilValue = filtered?.joinToString("")
        )
    }
    return boardCopy
}

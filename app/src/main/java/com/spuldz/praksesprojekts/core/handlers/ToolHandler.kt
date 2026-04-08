package com.spuldz.praksesprojekts.core.handlers

import com.spuldz.praksesprojekts.core.models.GridCellModel

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

fun addHintToBoard(
    board: MutableList<MutableList<GridCellModel>>?,
    solution: MutableList<MutableList<GridCellModel>>?,
    cell: GridCellModel?
) : MutableList<MutableList<GridCellModel>>{
    if (board == null) return mutableListOf()
    if (cell == null) return mutableListOf()

    val boardCopy = copyBoard(board)
    val row: Int = cell.rowNumber
    val col: Int = cell.colNumber
    val solutionValue = solution?.get(row)[col]?.value

    cell.copy(
        value = solutionValue ?: cell.value,
        isPlayerPlaced = true,
        isEditable = false,
        isSelected = true
    ).let { boardCopy[row][col] = it }

    return lightUpAllCells(boardCopy, solutionValue)
}

package com.spuldz.praksesprojekts.core.handlers

import com.spuldz.praksesprojekts.core.models.GridCellModel

class ToolHandler {
    val generationHandler = GameBoardGenerationHandler()
    val gameplayHandler = GameplayHandler()
    fun enterPencilNumber(
        board: MutableList<MutableList<GridCellModel>>,
        number: Int,
        cell: GridCellModel
    ) : MutableList<MutableList<GridCellModel>> {
        var content = cell.pencilValue
        val contentList = content?.trim()?.split("")
        val boardCopy = generationHandler.copyBoard(board)

        if (
            contentList?.contains(number.toString()) == true ||
            !generationHandler.isValid(board, cell.rowNumber, cell.colNumber, number)
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

        val boardCopy = generationHandler.copyBoard(board).toMutableList()
        val cells = boardCopy.flatten().filter { it.pencilValue != null && it.value == 0}

        cells.forEach { c ->
            val list = c.pencilValue?.trim()?.map { it.toString() }
            val filtered = list?.filter { generationHandler.isValid(boardCopy, c.rowNumber, c.colNumber, it.toInt()) }
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
    ) : MutableList<MutableList<GridCellModel>> {
        if (board == null) return mutableListOf()
        if (cell == null) return mutableListOf()

        val boardCopy = generationHandler.copyBoard(board)
        val row: Int = cell.rowNumber
        val col: Int = cell.colNumber
        val solutionValue = solution?.get(row)[col]?.value

        cell.copy(
            value = solutionValue ?: cell.value,
            isPlayerPlaced = true,
            isEditable = false,
            isSelected = true
        ).let { boardCopy[row][col] = it }

        return gameplayHandler.lightUpAllCells(boardCopy, solutionValue)
    }

    fun getPencilRows(cell: GridCellModel) : MutableList<MutableList<String>> {
        if (cell.pencilValue == null) return mutableListOf()

        val numbers = cell.pencilValue.trim().split("")
        val row1 = mutableListOf<String>()
        val row2 = mutableListOf<String>()
        val row3 = mutableListOf<String>()

        for (i in 1..3) {
            if (i <= numbers.size - 1) {
                row1.add(numbers[i])
            } else break
        }

        for (i in 4..6) {
            if (i <= numbers.size - 1) {
                row2.add(numbers[i])
            } else break
        }

        for (i in 7..9) {
            if (i <= numbers.size - 1) {
                row3.add(numbers[i])
            } else break
        }
        return mutableListOf(row1, row2, row3)
    }

}

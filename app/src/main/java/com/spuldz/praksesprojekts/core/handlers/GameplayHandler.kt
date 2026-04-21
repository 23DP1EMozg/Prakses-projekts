package com.spuldz.praksesprojekts.core.handlers

import com.spuldz.praksesprojekts.core.models.GameInputModel
import com.spuldz.praksesprojekts.core.models.GridCellModel

class GameplayHandler {
    fun isNumberComplete(number: Int, board: MutableList<MutableList<GridCellModel>>) : Boolean {
        var count = 0
        board.forEach { row ->
            row.forEach { cell ->
                if (cell.value == number) count++
            }
        }

        return count == 9
    }

fun isBoardComplete(board: MutableList<MutableList<GridCellModel>>?): Boolean {
    val emptyCell = board?.flatten()?.firstOrNull {it.value == 0}
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

    fun lightUpAllCells(board: MutableList<MutableList<GridCellModel>>, number: Int?): MutableList<MutableList<GridCellModel>> {
        val boardCopy = copyBoard(board)
        boardCopy.forEach { row ->
            row.forEach { cell ->
                if (cell.value == number) {
                    cell.isLightUp = true
                }
            }
        }
        return boardCopy
    }
}

package com.spuldz.praksesprojekts.core.repositories

import android.text.format.DateUtils
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.handlers.copyBoard
import com.spuldz.praksesprojekts.core.handlers.getFilledBoard
import com.spuldz.praksesprojekts.core.handlers.removeCellsFromBoard
import com.spuldz.praksesprojekts.core.models.GameModel
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
    private val _game = MutableStateFlow<GameModel?>(null)
    private var solution: MutableList<MutableList<GridCellModel>>? = null
    val gameBoard = _gameBoard.asStateFlow()
    val game = _game.asStateFlow()

     fun fillGameBoard(difficulty: String){
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

            _game.update { GameModel(difficulty = difficulty) }
            _gameBoard.update { boardWithRemovedCells.map { it.toList() }.toList() }

            startGameTimer()
    }

     fun startGameTimer() {
         launchDefault {
             while (!_game.value?.isFinished!!) {
                 delay(1000)
                 updateGameTimerByOneSecond()
             }
         }
    }

    fun selectCell(cell: GridCellModel) {

        if (_game.value?.isFinished == true) return

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

    suspend fun addNumberToSelectedCell(number: Int) {
        if (_game.value?.isFinished == true) return

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
                )

                newBoard.forEach { row ->
                    row.forEach { cell -> cell.isEditable = false }
                }

                _gameBoard.update { copyBoard(newBoard) }
                _game.update { _game.value?.copy(
                    mistakes = _game.value?.mistakes?.plus(1) ?: 0
                ) }

                if (_game.value?.mistakes!! >= 3) {
                    _game.update { _game.value?.copy(
                        isFinished = true
                    ) }
                    Timber.d("You Lose!")
                }

                delay(2000)
                newBoard[row][col] = selectedCell.copy(
                    value = 0,
                    isError = false,
                    isPlayerPlaced = false
                )

                newBoard.forEach { row ->
                    row.forEach { cell -> cell.isEditable = true }
                }
            }
           _gameBoard.update { copyBoard(newBoard) }
      }

    fun updateGameTimerByOneSecond(){
        var seconds = _game.value?.seconds ?: return
        seconds++
        val formated = DateUtils.formatElapsedTime(seconds)
        _game.update { _game.value?.copy(
            time = formated,
            seconds = seconds
        )
        }
    }
}

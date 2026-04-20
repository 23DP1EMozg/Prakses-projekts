package com.spuldz.praksesprojekts.core.repositories

import android.text.format.DateUtils
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.handlers.ToolHandler
import com.spuldz.praksesprojekts.core.handlers.copyBoard
import com.spuldz.praksesprojekts.core.handlers.getFilledBoard
import com.spuldz.praksesprojekts.core.handlers.isBoardComplete
import com.spuldz.praksesprojekts.core.handlers.isNumberComplete
import com.spuldz.praksesprojekts.core.handlers.lightUpAllCells
import com.spuldz.praksesprojekts.core.handlers.removeCellsFromBoard
import com.spuldz.praksesprojekts.core.handlers.updateGameInputs
import com.spuldz.praksesprojekts.core.models.GameInputModel
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
    private val _inputs = MutableStateFlow<List<GameInputModel>?>(null)
    private var solution: MutableList<MutableList<GridCellModel>>? = null
    val gameBoard = _gameBoard.asStateFlow()
    val game = _game.asStateFlow()
    val inputs = _inputs.asStateFlow()
    val toolHandler = ToolHandler()

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
            var inputList: MutableList<GameInputModel>? = mutableListOf()

            for (num in 1..9) {
                inputList?.add(GameInputModel(value = num))

                if (isNumberComplete(num, boardWithRemovedCells)) {
                    inputList = updateGameInputs(num, inputList)
                }
            }

            _game.update { GameModel(difficulty = difficulty) }
            _gameBoard.update { boardWithRemovedCells.map { it.toList() }.toList() }
            _inputs.update { inputList }

            startGameTimer()
    }

     fun startGameTimer() {
         val startTime = System.currentTimeMillis()
         launchDefault {
             while (_game.value?.isFinished == false) {
                 val elapsedSeconds = ((System.currentTimeMillis() - startTime) / 1000)
                 val formatedTime = DateUtils.formatElapsedTime(elapsedSeconds)

                 _game.update {
                     it?.copy(
                         time = formatedTime,
                         seconds = elapsedSeconds
                     )
                 }
                 delay(1000)
             }
         }
    }

    fun selectCell(cell: GridCellModel) {
        if (_game.value?.isFinished == true) return

        val selectedRow = cell.rowNumber
        val selectedCol = cell.colNumber
        val selectedValue = cell.value

        val newBoard = _gameBoard.value?.mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, c ->

                val isSelected = rowIndex == selectedRow && colIndex == selectedCol

                val isLightUp =
                    selectedValue != 0 && c.value == selectedValue

                val isHighlighted =
                    rowIndex == selectedRow || colIndex == selectedCol

                c.copy(
                    isSelected = isSelected,
                    isLightUp = isLightUp,
                    isHighlighted = isHighlighted
                )
            }.toMutableList()
        }?.toMutableList()

        if (_game.value?.hintMode == true && selectedValue == 0) {

            if (_game.value?.hintsLeft == 0) {
                return
            }

            _gameBoard.update { toolHandler.addHintToBoard(newBoard, solution, newBoard?.get(selectedRow)[selectedCol]) }
            _game.update {
                it?.copy(
                    hintsLeft = it.hintsLeft - 1
                )
            }
            return
        }

        Timber.d(newBoard?.get(selectedRow)?.get(selectedCol).toString())

        _gameBoard.update { newBoard }
    }
    suspend fun addNumberToSelectedCell(number: Int) {
        if (_game.value?.isFinished == true) return

        var newBoard = _gameBoard.value?.map { it.toMutableList() }?.toMutableList()
            val selectedCell = newBoard
                ?.flatten()
                ?.firstOrNull {it.isSelected}

      fun addNumberToSelectedCell(number: Int) {
        val newBoard = _gameBoard.value?.map { it.toMutableList() }?.toMutableList()
        val selectedCellTemp = selectedCell ?: return
        if (newBoard == null) return
        if (!selectedCellTemp.isEditable) return

            if (selectedCell == null) return
            if (!selectedCell.isEditable) return

            val row = selectedCell.rowNumber
            val col = selectedCell.colNumber

            if (solution != null) {
                for (row in solution) {
                    Timber.d(row.map { it.value }.toString())
                }
            }

            if (_game.value?.pencilMode == true && selectedCell.value == 0) {
                _gameBoard.update { toolHandler.enterPencilNumber(newBoard, number, selectedCell) }
                return
            }

            if (solution?.get(row)[col]?.value == number) {
                newBoard[row][col] = selectedCell.copy(
                    value = number,
                    isPlayerPlaced = true,
                    isEditable = false
                )

                newBoard = lightUpAllCells(newBoard, number)

                if(isNumberComplete(number,newBoard)) {
                    val inputsCopy = updateGameInputs(number, _inputs.value)
                    _inputs.update { inputsCopy }
                }

                if (isBoardComplete(newBoard)) {
                    _game.update { _game.value?.copy(
                        isFinished = true
                    ) }
                    Timber.d("You Win!")
                }
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
           _gameBoard.update { toolHandler.updatePencilEnteredNumbers(copyBoard(newBoard)) }
      }

    fun togglePencilMode() {
        _game.update { it?.copy(
            pencilMode = !it.pencilMode,
            hintMode = false
        ) }
    }

    fun toggleHintMode() {
        _game.update { it?.copy(
            hintMode = !it.hintMode,
            pencilMode = false
        ) }
    }

    fun getPencilGridRows(cell: GridCellModel): MutableList<MutableList<String>> {
        return toolHandler.getPencilRows(cell)
    }
}

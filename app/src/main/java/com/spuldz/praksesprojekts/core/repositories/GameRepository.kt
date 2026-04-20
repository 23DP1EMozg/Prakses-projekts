package com.spuldz.praksesprojekts.core.repositories

import android.text.format.DateUtils
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.database.dao.PreferencesDAO
import com.spuldz.praksesprojekts.core.database.dao.ScoreDAO
import com.spuldz.praksesprojekts.core.database.entities.Preferences
import com.spuldz.praksesprojekts.core.handlers.addHintToBoard
import com.spuldz.praksesprojekts.core.handlers.copyBoard
import com.spuldz.praksesprojekts.core.handlers.enterPencilNumber
import com.spuldz.praksesprojekts.core.handlers.getFilledBoard
import com.spuldz.praksesprojekts.core.handlers.getPencilRows
import com.spuldz.praksesprojekts.core.handlers.isBoardComplete
import com.spuldz.praksesprojekts.core.handlers.isNumberComplete
import com.spuldz.praksesprojekts.core.handlers.lightUpAllCells
import com.spuldz.praksesprojekts.core.handlers.removeCellsFromBoard
import com.spuldz.praksesprojekts.core.handlers.updateGameInputs
import com.spuldz.praksesprojekts.core.handlers.updatePencilEnteredNumbers
import com.spuldz.praksesprojekts.core.models.GameInputModel
import com.spuldz.praksesprojekts.core.models.GameModel
import com.spuldz.praksesprojekts.core.models.GridCellModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val preferencesDao: PreferencesDAO,
    private val scoreDao: ScoreDAO
) {
    private val _gameBoard = MutableStateFlow<List<List<GridCellModel>>?>(null)
    private val _game = MutableStateFlow<GameModel?>(null)
    private val _inputs = MutableStateFlow<List<GameInputModel>?>(null)
    private val _preferences = MutableStateFlow(Preferences())
    private var solution: MutableList<MutableList<GridCellModel>>? = null
    val gameBoard = _gameBoard.asStateFlow()
    val game = _game.asStateFlow()
    val inputs = _inputs.asStateFlow()
    val preferences = _preferences.asStateFlow()

    fun updateInputLayout() {
        launchDefault {
            val prefs = preferencesDao.getPreferences()
            Timber.d("PREFERENCES: %s", prefs.toString())

            if (prefs == null) return@launchDefault

            _preferences.update { prefs }
        }
    }
     suspend fun fillGameBoard(difficulty: String){
            val board = getFilledBoard()
            solution = board
            val amount = when(difficulty) {
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

            withContext(Dispatchers.IO) {
                _game.update {
                    GameModel(
                        difficulty = difficulty,
                        hintsLeft = preferencesDao.getPreferences()?.hintCount ?: 3,
                        hintCount = preferencesDao.getPreferences()?.hintCount ?: 3,
                        mistakeLimit = preferencesDao.getPreferences()?.mistakeLimit ?: 3
                    )
                }
            }
            _gameBoard.update { boardWithRemovedCells.map { it.toList() }.toList() }
            _inputs.update { inputList }

            startGameTimer()
    }

     suspend fun startGameTimer() {
         val startTime = System.currentTimeMillis()
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
            val updatedBoard = addHintToBoard(newBoard, solution, newBoard?.get(selectedRow)[selectedCol])
            val win = isBoardComplete(updatedBoard)
            _gameBoard.update { updatedBoard }
            _game.update { it?.copy(
                hintsLeft = it.hintsLeft - 1,
                isFinished = win,
                isWin = win
            ) }
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

            if (_game.value?.pencilMode == true && selectedCell.value == 0) {
                _gameBoard.update { enterPencilNumber(newBoard, number, selectedCell) }
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
                        isFinished = true,
                        isWin = true
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

                    if (_game.value?.mistakes!! >= _game.value?.mistakeLimit!!) {
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
           _gameBoard.update { updatePencilEnteredNumbers(copyBoard(newBoard)) }
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
        return getPencilRows(cell)
    }

}

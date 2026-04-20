package com.spuldz.praksesprojekts.ui.screens.game

import androidx.lifecycle.ViewModel
import com.spuldz.praksesprojekts.core.common.launch
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.models.GridCellModel
import com.spuldz.praksesprojekts.core.repositories.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
) : ViewModel() {
    val gameBoard = gameRepository.gameBoard
    val game = gameRepository.game
    val inputs = gameRepository.inputs
    val preferences = gameRepository.preferences
    private var timerJob: Job? = null
    fun generateGameBoard(difficulty: String) {
        launchDefault {
            gameRepository.fillGameBoard(difficulty)
            startTimer()
        }
    }

    fun selectCell(cell: GridCellModel) {
        Timber.d(cell.toString())
        gameRepository.selectCell(cell)
    }

    fun addNumberToSelectedCell(number: Int) = launch {
        gameRepository.addNumberToSelectedCell(number)
    }

    fun togglePencilMode() {
        gameRepository.togglePencilMode()
    }

    fun onHint() {
        gameRepository.toggleHintMode()
    }

    fun getPencilGridRows(cell: GridCellModel) : MutableList<MutableList<String>> {
      return gameRepository.getPencilGridRows(cell)
    }

    fun updateInputLayout() {
        gameRepository.updateInputLayout()
    }

    private fun startTimer() {
        timerJob?.cancel()

        timerJob = launch {
            val startTime = System.currentTimeMillis()

            while (game.value?.isFinished == false) {
                val elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000
                gameRepository.updateGameTimer(elapsedSeconds)
                delay(1000)
            }
        }
    }
}

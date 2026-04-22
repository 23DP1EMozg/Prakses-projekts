package com.spuldz.praksesprojekts.ui.screens.game

import androidx.lifecycle.ViewModel
import com.spuldz.praksesprojekts.core.common.launch
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.database.dao.GameStateDAO
import com.spuldz.praksesprojekts.core.database.entities.GameState
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
    private val gameStateDao: GameStateDAO
) : ViewModel() {
    val gameBoard = gameRepository.gameBoard
    val game = gameRepository.game
    val inputs = gameRepository.inputs
    val preferences = gameRepository.preferences
    private var timerJob: Job? = null
    fun generateGameBoard(difficulty: String, loadedGame: Boolean) {
        launchDefault {
            val gameState = gameStateDao.getGameState()
            gameRepository.fillGameBoard(difficulty, loadedGame, gameState)
            startTimer(loadedGame, gameState)
        }
    }

    fun selectCell(cell: GridCellModel) {
        Timber.d(cell.toString())
        launch {
            gameRepository.selectCell(cell)
        }
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

    private fun startTimer(loadedGame: Boolean, gameState: GameState?) {
        timerJob?.cancel()
        timerJob = launch {

            val previouslyPlayedSeconds = if (loadedGame) {
                gameState?.game?.seconds ?: 0L
            } else {
                0L
            }

            val virtualStartTime = System.currentTimeMillis() - (previouslyPlayedSeconds * 1000)
            while (game.value?.isFinished == false) {
                val elapsedSeconds = (System.currentTimeMillis() - virtualStartTime) / 1000
                gameRepository.updateGameTimer(elapsedSeconds)
                delay(1000)
            }
        }
    }
}

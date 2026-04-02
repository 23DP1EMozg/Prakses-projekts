package com.spuldz.praksesprojekts.ui.screens.game

import androidx.lifecycle.ViewModel
import com.spuldz.praksesprojekts.core.common.launch
import com.spuldz.praksesprojekts.core.models.GridCellModel
import com.spuldz.praksesprojekts.core.repositories.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {
    val gameBoard = gameRepository.gameBoard

    fun generateGameBoard(difficulty: String) = launch {
        gameRepository.fillGameBoard(difficulty)
    }

    fun selectCell(cell: GridCellModel) {
        Timber.d(cell.toString())
        gameRepository.selectCell(cell)
    }

    fun addNumberToSelectedCell(number: Int) = launch {
        gameRepository.addNumberToSelectedCell(number)
    }
}

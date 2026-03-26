package com.spuldz.praksesprojekts.ui.screens.game

import androidx.lifecycle.ViewModel
import com.spuldz.praksesprojekts.core.repositories.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {
    val gameBoard = gameRepository.gameBoard

    fun generateGameBoard() = gameRepository.fillGameBoard(
        gameRepository.generateBoilerplate()
    )
}
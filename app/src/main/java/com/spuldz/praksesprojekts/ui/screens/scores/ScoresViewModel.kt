package com.spuldz.praksesprojekts.ui.screens.scores

import androidx.lifecycle.ViewModel
import com.spuldz.praksesprojekts.core.common.launch
import com.spuldz.praksesprojekts.core.repositories.ScoresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoresViewModel @Inject constructor(
    private val scoresRepository: ScoresRepository
) : ViewModel(){
    val scores = scoresRepository.scores

    fun getAllScores() {
        launch {
            scoresRepository.getAllScores()
        }
    }
}

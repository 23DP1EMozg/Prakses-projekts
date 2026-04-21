package com.spuldz.praksesprojekts.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spuldz.praksesprojekts.core.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel(){
    val savedGame = homeRepository.savedGame

    fun checkForSavedGame() {
        viewModelScope.launch {
            homeRepository.checkForSavedGame()
        }
    }
}
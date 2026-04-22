package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.database.dao.GameStateDAO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val gameStateDao: GameStateDAO
){
    private val _savedGame = MutableStateFlow(false)
    val savedGame = _savedGame.asStateFlow()

    suspend fun checkForSavedGame() {
        val savedGame = gameStateDao.getGameState()
        _savedGame.update { savedGame != null }
        Timber.d("SAVED GAME: $savedGame")
    }
}

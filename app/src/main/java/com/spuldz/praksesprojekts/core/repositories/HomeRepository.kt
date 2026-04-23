package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.database.dao.GameStateDAO
import com.spuldz.praksesprojekts.core.database.dao.UserDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val gameStateDao: GameStateDAO,
    private val userDAO: UserDAO
){
    private val _savedGame = MutableStateFlow(false)
    val savedGame = _savedGame.asStateFlow()

    suspend fun checkForSavedGame() {
        withContext(Dispatchers.IO) {
            val savedGame = gameStateDao.getGameStateFromUserId(userDAO.getLoggedInUser()?.id)
            _savedGame.update { savedGame != null }
            Timber.d("SAVED GAME: $savedGame")
        }
    }
}

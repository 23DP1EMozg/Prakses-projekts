package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.database.dao.ScoreDAO
import com.spuldz.praksesprojekts.core.database.dao.UserDAO
import com.spuldz.praksesprojekts.core.database.entities.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoresRepository @Inject constructor(
    private val scoreDao: ScoreDAO,
    private val userDao: UserDAO
){
    private val _scores = MutableStateFlow<List<Score>>(listOf())
    val scores = _scores.asStateFlow()

    suspend fun getAllScores(){
        withContext(Dispatchers.IO) {
            val userId = userDao.getLoggedInUser()?.id
            val s = scoreDao.getAllUsersScores(userId)
            _scores.update { s }
        }
    }
}

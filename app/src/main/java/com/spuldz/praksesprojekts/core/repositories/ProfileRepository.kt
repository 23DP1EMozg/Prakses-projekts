package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.database.dao.UserDAO
import com.spuldz.praksesprojekts.core.database.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val userDAO: UserDAO
){

    private val _user = MutableStateFlow(User(
       0,
        null,
        null,
        null,
        null
    ))

    val user = _user.asStateFlow()

    fun setUser() {
        val u = userDAO.getLoggedInUser() ?: return
        _user.update { u }
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            userDAO.logout()
        }
    }
}

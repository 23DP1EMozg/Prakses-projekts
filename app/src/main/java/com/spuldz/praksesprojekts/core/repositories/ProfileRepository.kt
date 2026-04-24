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
    val _userUpdate = MutableStateFlow(
        User(
            0,
            "",
            "",
            null,
            null
        )
    )
    val userUpdate = _userUpdate.asStateFlow()
    val _changes = MutableStateFlow(false)
    val changes = _changes.asStateFlow()
    suspend fun setUser() {
        withContext(Dispatchers.IO) {
            val u = userDAO.getLoggedInUser() ?: return@withContext
            _user.update { u }
            _userUpdate.update { u }
        }
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            userDAO.logout()
        }
    }

    private fun checkForChanges(user: User, dbUser: User?) : Boolean{
        return user.password != dbUser?.password ||
                user.username != dbUser?.username
    }

    suspend fun updateUsername(username: String) {
        withContext(Dispatchers.IO) {
            _userUpdate.update {
                it.copy(
                    username = username
                )
            }

            _changes.update {
                checkForChanges(_userUpdate.value, userDAO.getLoggedInUser())
            }
        }
    }

    suspend fun updatePassword(password: String) {
        withContext(Dispatchers.IO) {
            _userUpdate.update {
                it.copy(
                    password = password
                )
            }

            _changes.update {
                checkForChanges(_userUpdate.value, userDAO.getLoggedInUser())
            }
        }
    }

    suspend fun saveChanges() {
        withContext(Dispatchers.IO) {
            if(
                _userUpdate.value.password?.length!! >= 7 &&
                _userUpdate.value.username?.length!! >= 7 &&
                userDAO.getUserByUsername(_userUpdate.value.username!!) == null
            ) {
                userDAO.deleteLoggedInUser()
                userDAO.insert(_userUpdate.value)
                _user.update { _userUpdate.value }
                _changes.update { false }
            }
        }
    }
}

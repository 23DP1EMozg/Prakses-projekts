package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.database.dao.UserDAO
import com.spuldz.praksesprojekts.core.database.entities.Preferences
import com.spuldz.praksesprojekts.core.database.entities.User
import com.spuldz.praksesprojekts.core.handlers.AuthHandler
import com.spuldz.praksesprojekts.core.models.Feedback
import com.spuldz.praksesprojekts.core.models.FeedbackType
import com.spuldz.praksesprojekts.core.models.RegisterForm
import com.spuldz.praksesprojekts.core.models.UpdateProperty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val userDao: UserDAO
){
    val authHandler = AuthHandler()
    private val _registerForm = MutableStateFlow(RegisterForm(
        username = "",
        password = "",
        passwordAgain = ""
    ))

    val registerForm = _registerForm.asStateFlow()

    private val _loginUsername = MutableStateFlow("")
    private val _loginPassword = MutableStateFlow("")

    val loginUsername = _loginUsername.asStateFlow()
    val loginPassword = _loginPassword.asStateFlow()

    private val _feedback = MutableStateFlow(Feedback(
        "",
        null
    ))
    val feedback = _feedback.asStateFlow()


    suspend fun createUser() {
        if (userDao.getUserByUsername(_registerForm.value.username) != null) {
            _feedback.update {
                Feedback(
                    message = "user with that username already exists",
                    feedbackType = FeedbackType.ERROR
                )
            }
            return
        }

        val f = authHandler.isUserValid(_registerForm.value)
        _feedback.update { f }
        if (f.feedbackType == FeedbackType.ERROR) return

        userDao.insert(
            User(
                username = _registerForm.value.username,
                password = _registerForm.value.password,
                loggedIn = false,
                preferences = Preferences()
            )
        )
    }

    fun updateRegisterForm(property: UpdateProperty, value: String) {
        _registerForm.update { prev ->
            when(property) {
                UpdateProperty.USERNAME -> prev.copy(username = value)
                UpdateProperty.PASSWORD -> prev.copy(password = value)
                UpdateProperty.PASSWORD_AGAIN -> prev.copy(passwordAgain = value)
            }
        }
    }

    suspend fun login() {
        val user = userDao.getUserByUsername(loginUsername.value)
        if (user == null) {
            _feedback.update {
                Feedback(
                    message = "user with such username doesn't exist",
                    feedbackType = FeedbackType.ERROR
                )
            }
            return
        }

        if (user.password != loginPassword.value) {
            _feedback.update {
                Feedback(
                    message = "wrong password!",
                    feedbackType = FeedbackType.ERROR
                )
            }
            return
        }

        userDao.updateLoggedIn(true, user.id)
    }
}

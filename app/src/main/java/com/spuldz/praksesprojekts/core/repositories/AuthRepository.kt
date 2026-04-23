package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.database.dao.UserDAO
import com.spuldz.praksesprojekts.core.database.entities.User
import com.spuldz.praksesprojekts.core.handlers.AuthHandler
import com.spuldz.praksesprojekts.core.models.Feedback
import com.spuldz.praksesprojekts.core.models.FeedbackType
import com.spuldz.praksesprojekts.core.models.LoginForm
import com.spuldz.praksesprojekts.core.models.Preferences
import com.spuldz.praksesprojekts.core.models.RegisterForm
import com.spuldz.praksesprojekts.core.models.UpdateProperty
import com.spuldz.praksesprojekts.ui.theme.setTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import timber.log.Timber
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
    private val _loginForm = MutableStateFlow(LoginForm(
        username = "",
        password = ""
    ))
    val loginForm = _loginForm.asStateFlow()

    private val _feedback = MutableStateFlow(Feedback(
        "",
        null
    ))
    val feedback = _feedback.asStateFlow()

    val _loggedIn = MutableStateFlow(false)
    val loggedIn = _loggedIn.asStateFlow()


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
        userDao.logout()
        userDao.insert(
            User(
                username = _registerForm.value.username,
                password = _registerForm.value.password,
                loggedIn = true,
                preferences = Preferences()
            )
        )
        _loggedIn.update { true }
        _registerForm.update {
            it.copy(
                username = "",
                password = "",
                passwordAgain = ""
            )
        }
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

    fun updateLoginForm(property: UpdateProperty, value: String) {
        _loginForm.update { prev ->
            when(property) {
                UpdateProperty.USERNAME -> prev.copy(username = value)
                UpdateProperty.PASSWORD -> prev.copy(password = value)
                else -> prev.copy()
            }
        }
    }

    fun resetFeedback() {
        _feedback.update { Feedback(
            message = "",
            feedbackType = null
        ) }
    }

    suspend fun login() {
        val user = userDao.getUserByUsername(_loginForm.value.username)
        if (user == null) {
            _feedback.update {
                Feedback(
                    message = "user with such username doesn't exist",
                    feedbackType = FeedbackType.ERROR
                )
            }
            return
        }

        if (user.password != _loginForm.value.password) {
            _feedback.update {
                Feedback(
                    message = "wrong password!",
                    feedbackType = FeedbackType.ERROR
                )
            }
            return
        }
        userDao.logout()
        userDao.updateLoggedIn(true, user.id)
        _feedback.update {
            Feedback(
                message = "logged in succesfully, ${user.username}!",
                feedbackType = FeedbackType.SUCCESS
            )
        }
        _loggedIn.update { true }
        setTheme(user.preferences?.theme ?: 0)
        _loginForm.update {
            it.copy(
                username = "",
                password = ""
            )
        }

        withContext(Dispatchers.IO) {
            Timber.d("LOGGED IN USER: ${userDao.getLoggedInUser().toString()}")
        }
    }
}

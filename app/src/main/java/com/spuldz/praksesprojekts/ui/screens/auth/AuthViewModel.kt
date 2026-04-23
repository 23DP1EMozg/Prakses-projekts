package com.spuldz.praksesprojekts.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.spuldz.praksesprojekts.core.common.launch
import com.spuldz.praksesprojekts.core.models.UpdateProperty
import com.spuldz.praksesprojekts.core.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){

    val feedback = authRepository.feedback
    val registerForm = authRepository.registerForm
    val loginForm = authRepository.loginForm
    val loggedIn = authRepository.loggedIn
    fun updateRegisterForm(property: UpdateProperty, value: String) {
        authRepository.updateRegisterForm(property, value)
    }

    fun updateLoginForm(property: UpdateProperty, value: String) {
        authRepository.updateLoginForm(property, value)
    }

    fun createUser(nav: () -> Unit) {
        launch {
            authRepository.createUser()
            if (loggedIn.value) {
                nav()
            }
        }
    }

    fun login(nav: () -> Unit) {
        launch {
            authRepository.login()
            if (loggedIn.value) {
                nav()
            }
        }
    }

    fun resetFeedback() {
        authRepository.resetFeedback()
    }
}

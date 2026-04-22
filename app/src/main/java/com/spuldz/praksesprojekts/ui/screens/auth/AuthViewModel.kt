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
    fun updateRegisterForm(property: UpdateProperty, value: String) {
        authRepository.updateRegisterForm(property, value)
    }

    fun createUser() {
        launch {
            authRepository.createUser()
        }
    }

}

package com.spuldz.praksesprojekts.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.spuldz.praksesprojekts.core.common.launch
import com.spuldz.praksesprojekts.core.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(){

    val user = profileRepository.user
    val userUpdate = profileRepository.userUpdate
    val changes = profileRepository.changes
    fun logout() {
        launch {
            profileRepository.logout()
        }
    }

    fun setUser() {
        launch {
            profileRepository.setUser()
        }
    }

    fun updateUsername(value: String) {
        launch {
            profileRepository.updateUsername(value)
        }
    }

    fun updatePassword(value: String) {
        launch {
            profileRepository.updatePassword(value)
        }
    }

    fun saveChanges() {
        launch {
            profileRepository.saveChanges()
        }
    }
}
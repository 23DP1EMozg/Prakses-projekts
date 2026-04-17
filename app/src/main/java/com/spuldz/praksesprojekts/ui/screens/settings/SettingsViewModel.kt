package com.spuldz.praksesprojekts.ui.screens.settings

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.repositories.SettingsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val prefs = settingsRepository.prefs

    fun setLanguage(context: Context, languageCode: String) {
        viewModelScope.launch {
            settingsRepository.savePreferredLanguage(context, languageCode)
        }
    }

    fun setAppTheme(themeId: Int) {
        launchDefault {
            settingsRepository.setAppTheme(themeId)
        }
    }

    fun setGameInputLayout(layout: String) {
        launchDefault {
            settingsRepository.setGameInputLayout(layout)
        }
    }

    fun setHintCount(count: String) {
        launchDefault {
            settingsRepository.setHintCount(count)
        }
    }

    fun setMistakeLimit(limit: String) {
        launchDefault {
            settingsRepository.setMistakeLimit(limit)
        }
    }
}

package com.spuldz.praksesprojekts.ui.screens.settings

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.database.entities.Language
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    fun setLanguage(context: Context, languageCode: String) {
        val lang = Language(
            id = 1,
            languageCode = languageCode
        )
        viewModelScope.launch {
            settingsRepository.savePreferredLanguage(context, lang)
        }
    }

    fun setAppTheme(themeId: Int) {
        launchDefault {
            settingsRepository.setAppTheme(themeId)
        }
    }
}

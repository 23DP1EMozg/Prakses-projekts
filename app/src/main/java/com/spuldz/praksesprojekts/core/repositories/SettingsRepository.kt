package com.spuldz.praksesprojekts.core.repositories

import android.app.Activity
import android.content.Context
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.database.dao.PreferencesDAO
import com.spuldz.praksesprojekts.core.database.entities.Preferences
import com.spuldz.praksesprojekts.ui.theme.setTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    val preferencesDao: PreferencesDAO
){
    private val _prefs = MutableStateFlow(Preferences())
    val prefs = _prefs.asStateFlow()

    init {
        launchDefault {
            _prefs.update { preferencesDao.getPreferences() }
        }
    }
    suspend fun savePreferredLanguage(context: Context, language: String) {
        val previousLanguage = withContext(Dispatchers.IO) {
            preferencesDao.getPreferences().languageCode
        }

        if (previousLanguage == language) return

        withContext(Dispatchers.IO) {
            preferencesDao.updateLanguage(language)
        }

        withContext(Dispatchers.Main) {
            val activity = context as? Activity
            activity?.recreate()
        }
    }

    suspend fun setAppTheme(themeId: Int) {
        withContext(Dispatchers.IO) {
            preferencesDao.updateTheme(themeId)
        }
        setTheme(themeId)
    }

    suspend fun setGameInputLayout(layout: String) {
        withContext(Dispatchers.IO) {
            preferencesDao.updateInputLayout(layout)
            _prefs.update {
                it.copy(
                    inputLayout = layout
                )
            }
        }
    }
}
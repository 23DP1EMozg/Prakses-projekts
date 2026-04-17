package com.spuldz.praksesprojekts.core.repositories

import android.app.Activity
import android.content.Context
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.database.dao.PreferencesDAO
import com.spuldz.praksesprojekts.core.database.entities.Preferences
import com.spuldz.praksesprojekts.core.handlers.SettingsHandler
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
class SettingsRepository @Inject constructor(
    val preferencesDao: PreferencesDAO
){
    private val _prefs = MutableStateFlow(Preferences())
    val prefs = _prefs.asStateFlow()
    val settingsHandler = SettingsHandler()

    init {
        launchDefault {
            val p = preferencesDao.getPreferences() ?: return@launchDefault
            _prefs.update { p }
        }
    }
    suspend fun savePreferredLanguage(context: Context, language: String) {
        val previousLanguage = withContext(Dispatchers.IO) {
            preferencesDao.getPreferences()?.languageCode
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

    suspend fun setHintCount(count: String) {
        withContext(Dispatchers.IO) {
            val num = settingsHandler.validateNumberInput(
                newValue = count,
                oldValue = _prefs.value.hintCount,
                maxLength = 81
            )
            preferencesDao.updateHintCount(num)
            _prefs.update {
                it.copy(
                    hintCount = num
                )
            }
            Timber.d(_prefs.value.hintCount.toString())
        }
    }

    suspend fun setMistakeLimit(limit: String) {
        withContext(Dispatchers.IO) {
            val num = settingsHandler.validateNumberInput(
                newValue = limit,
                oldValue = _prefs.value.mistakeLimit,
                maxLength = 81
            )
            preferencesDao.updateMistakeLimit(num)
            _prefs.update {
                it.copy(
                    mistakeLimit = num
                )
            }
        }
    }
}

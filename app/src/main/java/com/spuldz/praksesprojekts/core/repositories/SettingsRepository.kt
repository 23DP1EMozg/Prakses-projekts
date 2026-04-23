package com.spuldz.praksesprojekts.core.repositories

import android.app.Activity
import android.content.Context
import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.database.dao.UserDAO
import com.spuldz.praksesprojekts.core.handlers.SettingsHandler
import com.spuldz.praksesprojekts.core.models.Preferences
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
    val userDao: UserDAO
){
    private val _prefs = MutableStateFlow(Preferences())
    private val _changes = MutableStateFlow(false)
    val prefs = _prefs.asStateFlow()
    val changes = _changes.asStateFlow()
    val settingsHandler = SettingsHandler()

    init {
        launchDefault {
            val p = userDao.getLoggedInUser()?.preferences ?: Preferences()
            _prefs.update { p }
        }
    }

    suspend fun resetInputs() {
        withContext(Dispatchers.IO) {
            val p = userDao.getLoggedInUser()?.preferences ?: return@withContext
            _prefs.update { p }
            _changes.update { false }
        }
    }

    suspend fun savePreferredLanguage(context: Context, language: String) {
        val previousLanguage = withContext(Dispatchers.IO) {
            userDao.getLoggedInUser()?.preferences?.languageCode
        }

        if (previousLanguage == language) return

        withContext(Dispatchers.IO) {
            val user = userDao.getLoggedInUser() ?: return@withContext
            val currentPrefs = user.preferences ?: Preferences()
            
            if (currentPrefs.languageCode == language) return@withContext

            val updatedPrefs = currentPrefs.copy(languageCode = language)
            userDao.insertLoggedInUserPreferences(updatedPrefs)
        }

        withContext(Dispatchers.Main) {
            val activity = context as? Activity
            activity?.recreate()
        }
    }

    suspend fun setAppTheme(themeId: Int) {
        withContext(Dispatchers.IO) {
            val user = userDao.getLoggedInUser() ?: return@withContext
            val currentPrefs = user.preferences ?: Preferences()
            val updatedPrefs = currentPrefs.copy(theme = themeId)
            userDao.insertLoggedInUserPreferences(updatedPrefs)
        }
        setTheme(themeId)
    }

    suspend fun setGameInputLayout(layout: String) {
        withContext(Dispatchers.IO) {
            val user = userDao.getLoggedInUser() ?: return@withContext
            val currentPrefs = user.preferences ?: Preferences()
            val updatedPrefs = currentPrefs.copy(inputLayout = layout)
            userDao.insertLoggedInUserPreferences(updatedPrefs)
            
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
            //preferencesDao.updateHintCount(num)
            _prefs.update {
                it.copy(
                    hintCount = num
                )
            }
            _changes.update {
                settingsHandler.checkForChanges(
                    _prefs.value,
                    userDao.getLoggedInUser()?.preferences
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
            _prefs.update {
                it.copy(
                    mistakeLimit = num
                )
            }
            _changes.update {
                settingsHandler.checkForChanges(
                    _prefs.value,
                    userDao.getLoggedInUser()?.preferences
                )
            }
        }
    }

    suspend fun saveGameplaySettings() {
        withContext(Dispatchers.IO) {
            val user = userDao.getLoggedInUser() ?: return@withContext
            val currentPrefs = user.preferences ?: Preferences()
            val updatedPrefs = currentPrefs.copy(
                hintCount = _prefs.value.hintCount,
                mistakeLimit = _prefs.value.mistakeLimit
            )
            userDao.insertLoggedInUserPreferences(updatedPrefs)
            _changes.update { false }
        }
    }
}

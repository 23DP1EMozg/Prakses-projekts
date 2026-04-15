package com.spuldz.praksesprojekts.ui.screens.settings

import android.app.Activity
import android.content.Context
import com.spuldz.praksesprojekts.core.database.dao.PreferencesDAO
import com.spuldz.praksesprojekts.ui.theme.setTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    val preferencesDao: PreferencesDAO
){
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
}

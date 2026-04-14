package com.spuldz.praksesprojekts.ui.screens.settings

import android.app.Activity
import android.content.Context
import com.spuldz.praksesprojekts.core.database.dao.LanguageDAO
import com.spuldz.praksesprojekts.core.database.dao.PreferencesDAO
import com.spuldz.praksesprojekts.core.database.entities.Language
import com.spuldz.praksesprojekts.ui.theme.setTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    val languageDao: LanguageDAO,
    val preferencesDao: PreferencesDAO
){
    suspend fun savePreferredLanguage(context: Context, language: Language) {
        val previousLanguage = withContext(Dispatchers.IO) {
            languageDao.getLanguage()
        }

        if (previousLanguage?.languageCode == language.languageCode) return

        withContext(Dispatchers.IO) {
            languageDao.insert(language)
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

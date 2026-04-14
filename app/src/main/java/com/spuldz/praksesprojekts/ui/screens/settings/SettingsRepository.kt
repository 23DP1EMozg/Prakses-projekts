package com.spuldz.praksesprojekts.ui.screens.settings

import android.app.Activity
import android.content.Context
import com.spuldz.praksesprojekts.core.database.dao.LanguageDAO
import com.spuldz.praksesprojekts.core.database.entities.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    val db: LanguageDAO
){
    suspend fun savePreferredLanguage(context: Context, language: Language) {
        val previousLanguage = withContext(Dispatchers.IO) {
            db.getLanguage()
        }

        if (previousLanguage?.languageCode == language.languageCode) return

        withContext(Dispatchers.IO) {
            db.insert(language)
        }

        withContext(Dispatchers.Main) {
            val activity = context as? Activity
            activity?.recreate()
        }
    }
}

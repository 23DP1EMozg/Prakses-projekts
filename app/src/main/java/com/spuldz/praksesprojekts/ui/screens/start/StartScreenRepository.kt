package com.spuldz.praksesprojekts.ui.screens.start

import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.database.dao.PreferencesDAO
import com.spuldz.praksesprojekts.core.database.entities.Preferences
import com.spuldz.praksesprojekts.ui.theme.setTheme
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartScreenRepository @Inject constructor(
    val preferencesDao: PreferencesDAO
){
    init {
        Timber.d("HEYY IM INIT!")
        launchDefault {
            if (preferencesDao.getPreferences() == null) {
                launchDefault {
                    preferencesDao.insert(Preferences())
                }
            } else {
                val themeIndex = preferencesDao.getPreferences()?.theme ?: 0
                setTheme(themeIndex)
            }
        }
    }
}

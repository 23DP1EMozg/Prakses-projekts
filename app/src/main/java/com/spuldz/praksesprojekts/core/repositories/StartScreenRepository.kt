package com.spuldz.praksesprojekts.core.repositories

import com.spuldz.praksesprojekts.core.common.launchDefault
import com.spuldz.praksesprojekts.core.database.dao.UserDAO
import com.spuldz.praksesprojekts.core.models.Preferences
import com.spuldz.praksesprojekts.ui.theme.setTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartScreenRepository @Inject constructor(
    private val userDAO: UserDAO
){
    init {
        Timber.d("HEYY IM INIT!")
        launchDefault {
            if (userDAO.getLoggedInUser()?.preferences == null) {
                launchDefault {
                    userDAO.insertLoggedInUserPreferences(Preferences())
                }
            } else {
                val themeIndex = userDAO.getLoggedInUser()
                    ?.preferences?.theme ?: return@launchDefault
                setTheme(themeIndex)
            }
        }
    }

    fun redirect(
        toHome: () -> Unit,
        toLogin: () -> Unit
    ) {
        launchDefault {
            val user = userDAO.getLoggedInUser()
            withContext(Dispatchers.Main) {
                if (user == null) {
                    toLogin()
                } else {
                    toHome()
                }
            }
        }
    }
}

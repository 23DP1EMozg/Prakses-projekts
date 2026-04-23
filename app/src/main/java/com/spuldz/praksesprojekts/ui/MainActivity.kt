package com.spuldz.praksesprojekts.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.spuldz.praksesprojekts.core.common.LocaleManager
import com.spuldz.praksesprojekts.core.database.AppDatabase
import com.spuldz.praksesprojekts.core.models.Preferences
import com.spuldz.praksesprojekts.ui.navigation.NavigationHost
import com.spuldz.praksesprojekts.ui.theme.PraksesProjektsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
            // 1. Get the language code synchronously

            val db = Room.databaseBuilder(newBase, AppDatabase::class.java, "database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration(true)
                .build()


            val loggedInUser = db.userDao().getLoggedInUser()
            var prefs: Preferences?
            if (loggedInUser == null) {
                prefs = Preferences()
            } else {
                prefs = loggedInUser.preferences
            }

            val langCode = prefs?.languageCode ?: "en"
            db.close()

            // 3. Wrap and call super SYNC (No coroutines here!)
            val context = LocaleManager.wrap(newBase, langCode)
            super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraksesProjektsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        NavigationHost()
                    }
                }
            }
        }
    }
}

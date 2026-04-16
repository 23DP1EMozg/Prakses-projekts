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
import com.spuldz.praksesprojekts.ui.navigation.NavigationHost
import com.spuldz.praksesprojekts.ui.theme.PraksesProjektsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
            val tempDb = Room
                .databaseBuilder(
                    newBase,
                    AppDatabase::class.java,
                    "database"
                )
                .allowMainThreadQueries()
                .build()

            val langCode = tempDb.preferencesDao().getPreferences().languageCode
            tempDb.close()

            super.attachBaseContext(LocaleManager.wrap(newBase, langCode))
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

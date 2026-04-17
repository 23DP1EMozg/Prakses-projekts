package com.spuldz.praksesprojekts.core.database

import androidx.room.RoomDatabase
import com.spuldz.praksesprojekts.core.database.entities.Language
import androidx.room.Database
import com.spuldz.praksesprojekts.core.database.dao.PreferencesDAO
import com.spuldz.praksesprojekts.core.database.entities.Preferences

@Database(entities = [
        Language::class,
        Preferences::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun preferencesDao(): PreferencesDAO
}

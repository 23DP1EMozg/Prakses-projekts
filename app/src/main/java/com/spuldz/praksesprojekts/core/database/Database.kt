package com.spuldz.praksesprojekts.core.database

import androidx.room.RoomDatabase
import com.spuldz.praksesprojekts.core.database.dao.LanguageDAO
import com.spuldz.praksesprojekts.core.database.entities.Language
import androidx.room.Database

@Database(entities = [Language::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun languageDao(): LanguageDAO
}

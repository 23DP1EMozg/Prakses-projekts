package com.spuldz.praksesprojekts.core.database

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.spuldz.praksesprojekts.core.database.dao.GameStateDAO
import com.spuldz.praksesprojekts.core.database.dao.PreferencesDAO
import com.spuldz.praksesprojekts.core.database.dao.ScoreDAO
import com.spuldz.praksesprojekts.core.database.entities.GameState
import com.spuldz.praksesprojekts.core.database.entities.Preferences
import com.spuldz.praksesprojekts.core.database.entities.Score

@Database(entities = [
        Preferences::class,
        Score::class,
        GameState::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun preferencesDao(): PreferencesDAO
    abstract fun scoreDao(): ScoreDAO
    abstract fun gameStateDao(): GameStateDAO
}

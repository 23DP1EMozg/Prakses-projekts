package com.spuldz.praksesprojekts.core.database

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.spuldz.praksesprojekts.core.database.dao.GameStateDAO
import com.spuldz.praksesprojekts.core.database.dao.ScoreDAO
import com.spuldz.praksesprojekts.core.database.dao.UserDAO
import com.spuldz.praksesprojekts.core.database.entities.GameState
import com.spuldz.praksesprojekts.core.database.entities.Score
import com.spuldz.praksesprojekts.core.database.entities.User

@Database(entities = [
        Score::class,
        GameState::class,
        User::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDAO
    abstract fun gameStateDao(): GameStateDAO
    abstract fun userDao(): UserDAO
}

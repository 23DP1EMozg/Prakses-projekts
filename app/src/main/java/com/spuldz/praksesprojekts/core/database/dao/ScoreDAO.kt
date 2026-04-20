package com.spuldz.praksesprojekts.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.spuldz.praksesprojekts.core.database.entities.Score

@Dao
interface ScoreDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(score: Score)


}

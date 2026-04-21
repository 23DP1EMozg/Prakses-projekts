package com.spuldz.praksesprojekts.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spuldz.praksesprojekts.core.database.entities.GameState

@Dao
interface GameStateDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameState: GameState)

    @Query("SELECT * FROM gamestate WHERE id = 1 LIMIT 1")
    suspend fun getGameState() : GameState?

    @Query("DELETE FROM gamestate WHERE id = 1")
    suspend fun deleteGameState()
}

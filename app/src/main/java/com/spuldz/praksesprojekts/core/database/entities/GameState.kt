package com.spuldz.praksesprojekts.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spuldz.praksesprojekts.core.models.GameModel

@Entity
data class GameState(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "grid") val grid: String?,
    @ColumnInfo("game") val game: GameModel?
)

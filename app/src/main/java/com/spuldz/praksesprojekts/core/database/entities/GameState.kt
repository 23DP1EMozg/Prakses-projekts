package com.spuldz.praksesprojekts.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spuldz.praksesprojekts.core.models.GameInputModel
import com.spuldz.praksesprojekts.core.models.GameModel
import com.spuldz.praksesprojekts.core.models.GridCellModel

@Entity
data class GameState(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo(name = "grid") val grid: List<List<GridCellModel>>?,
    @ColumnInfo("game") val game: GameModel?,
    @ColumnInfo("inputs") val inputs: List<GameInputModel>?,
)

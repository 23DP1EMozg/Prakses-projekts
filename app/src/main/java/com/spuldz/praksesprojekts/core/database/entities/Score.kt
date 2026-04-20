package com.spuldz.praksesprojekts.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "seconds") val seconds: Int,
    @ColumnInfo(name = "difficulty") val difficulty: String
)

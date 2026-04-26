package com.spuldz.praksesprojekts.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "seconds") val seconds: Long,
    @ColumnInfo(name = "difficulty") val difficulty: String,
    @ColumnInfo(name = "user_id") val userId: Int?
)

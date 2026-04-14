package com.spuldz.praksesprojekts.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Language(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "language_code") val languageCode: String,
)

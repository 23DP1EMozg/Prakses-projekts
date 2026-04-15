package com.spuldz.praksesprojekts.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Locale

@Entity
data class Preferences(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "theme") val theme: Int = 0,
    @ColumnInfo(name = "input_layout") val inputLayout: String = "ROW",
    @ColumnInfo(name = "language_code") val languageCode: String = Locale.getDefault().language
)

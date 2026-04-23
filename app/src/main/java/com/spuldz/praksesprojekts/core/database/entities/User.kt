package com.spuldz.praksesprojekts.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spuldz.praksesprojekts.core.models.Preferences

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String?,
    val password: String?,
    val preferences: Preferences?,
    val loggedIn: Boolean?
)

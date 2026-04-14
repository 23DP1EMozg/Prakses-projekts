package com.spuldz.praksesprojekts.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spuldz.praksesprojekts.core.database.entities.Language

@Dao
interface LanguageDAO {
    @Query("SELECT * FROM language")
    suspend fun getAll(): List<Language>

    @Query("SELECT * FROM language WHERE id = 1 LIMIT 1")
    fun getLanguage(): Language?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(language: Language)
}
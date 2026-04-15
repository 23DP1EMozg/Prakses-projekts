package com.spuldz.praksesprojekts.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spuldz.praksesprojekts.core.database.entities.Preferences

@Dao
interface PreferencesDAO {
    @Query("SELECT * FROM preferences WHERE id = 1 LIMIT 1")
    fun getPreferences(): Preferences

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preferences: Preferences)

    @Query("UPDATE preferences SET theme = :themeId WHERE id = 1")
    fun updateTheme(themeId: Int)

    @Query("UPDATE preferences SET language_code = :code WHERE id = 1")
    fun updateLanguage(code: String)

    @Query("UPDATE preferences SET input_layout = :layout WHERE id = 1")
    fun updateInputLayout(layout: String)
}

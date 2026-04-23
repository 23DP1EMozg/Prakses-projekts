package com.spuldz.praksesprojekts.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spuldz.praksesprojekts.core.database.entities.User
import com.spuldz.praksesprojekts.core.models.Preferences

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers() : List<User>

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int) : User?

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String) : User?

    @Query("UPDATE user SET loggedIn = :status WHERE id = :userId")
    suspend fun updateLoggedIn(status: Boolean, userId: Int)

    @Query("SELECT * FROM user WHERE loggedIn = 1 LIMIT 1")
    fun getLoggedInUser() : User?

    @Query("UPDATE user SET preferences = :prefs WHERE loggedIn = 1")
    suspend fun insertLoggedInUserPreferences(prefs: Preferences)
    @Query("UPDATE user SET loggedIn = 0 WHERE loggedIn = 1")
    suspend fun logout()
}

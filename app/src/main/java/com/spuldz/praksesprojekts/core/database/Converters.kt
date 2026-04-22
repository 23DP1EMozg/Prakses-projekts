package com.spuldz.praksesprojekts.core.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spuldz.praksesprojekts.core.database.entities.Preferences
import com.spuldz.praksesprojekts.core.models.GameInputModel
import com.spuldz.praksesprojekts.core.models.GameModel
import com.spuldz.praksesprojekts.core.models.GridCellModel

class Converters {
    private val gson = Gson()

    //GAME
    @TypeConverter
    fun fromGameToJson(value: GameModel?) : String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromJsonToGame(value: String?) : GameModel? {
        return gson.fromJson(value, GameModel::class.java)
    }

    //GAME INPUT
    @TypeConverter
    fun fromInputListToJson(value: List<GameInputModel>?) : String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromJsonToInputList(value: String?) : List<GameInputModel>? {
        if (value == null) return null
        val type = object : TypeToken<List<GameInputModel>>() {}.type
        return gson.fromJson(value, type)
    }

    // GAME BOARD
    @TypeConverter
    fun fromGridToJson(value: List<List<GridCellModel>>?): String? = gson.toJson(value)

    @TypeConverter
    fun fromJsonToGrid(value: String?): List<List<GridCellModel>>? {
        if (value == null) return null
        val type = object : TypeToken<List<List<GridCellModel>>>() {}.type
        return gson.fromJson(value, type)
    }

    // PREFERENCES
    @TypeConverter
    fun fromPreferencesToJson(value: Preferences?) : String? = gson.toJson(value)

    @TypeConverter
    fun fromJsonToPreferences(value: String?) : Preferences? {
        if (value == null) return null
        return gson.fromJson(value, Preferences::class.java)
    }
}

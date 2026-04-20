package com.spuldz.praksesprojekts.core.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.spuldz.praksesprojekts.core.models.GameModel

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromGameToJson(value: GameModel?) : String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromJsonToGame(value: String?) : GameModel? {
        return gson.fromJson(value, GameModel::class.java)
    }
}

package com.siele.worldnews.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.siele.worldnews.data.model.Source

class Converters {
    @TypeConverter
    fun fromJsonSource(source: String):Source {
        return (Gson().fromJson(source,Source::class.java))
    }

    @TypeConverter
    fun toJsonSource(value:Source): String {
        return Gson().toJson(value)
    }
}
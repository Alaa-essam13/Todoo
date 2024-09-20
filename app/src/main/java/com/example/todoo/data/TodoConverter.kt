package com.example.todoo.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TodoConverter {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromBooleanList(value: List<Boolean>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toBooleanList(value: String): List<Boolean> {
        val listType = object : TypeToken<List<Boolean>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
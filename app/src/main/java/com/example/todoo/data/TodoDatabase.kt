package com.example.todoo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TodoEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(TodoConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract val dao: TodoDao
}
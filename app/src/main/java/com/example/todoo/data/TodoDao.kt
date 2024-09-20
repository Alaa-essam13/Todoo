package com.example.todoo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Upsert
    suspend fun upsertTodo(todoEntity: TodoEntity)

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)

    @Query("select * from todos order by id asc")
    fun getAllTodosById(): Flow<List<TodoEntity>>
}
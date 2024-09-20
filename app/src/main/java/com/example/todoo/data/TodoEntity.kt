package com.example.todoo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val listOfTasks:List<String>,
    val listOfTasksStatues:List<Boolean>
)

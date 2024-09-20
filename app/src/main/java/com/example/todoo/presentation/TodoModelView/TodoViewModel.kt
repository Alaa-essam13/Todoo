package com.example.todoo.presentation.TodoModelView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoo.data.TodoDao
import com.example.todoo.data.TodoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val todoDao:TodoDao):ViewModel() {
    private val _todos= MutableStateFlow<List<TodoEntity>>(emptyList())
    val todos:StateFlow<List<TodoEntity>> = _todos

    init {
        viewModelScope.launch {
            todoDao.getAllTodosById().collect{
                _todos.value=it
            }
        }
    }

    fun insertTodo(todo:TodoEntity){
        viewModelScope.launch {
            todoDao.upsertTodo(todo)
        }
    }

    fun deleteTodo(todo:TodoEntity){
        viewModelScope.launch {
            todoDao.deleteTodo(todo)
        }
    }

    fun updateTodo(todoEntity:TodoEntity,newstatuses:List<Boolean>){
        viewModelScope.launch {
            todoDao.upsertTodo(todoEntity=todoEntity.copy(listOfTasksStatues = newstatuses))
        }
    }

}
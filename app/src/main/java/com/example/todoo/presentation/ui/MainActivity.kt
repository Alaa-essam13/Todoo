package com.example.todoo.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.todoo.data.TodoDatabase
import com.example.todoo.presentation.TodoModelView.TodoViewModel
import com.example.todoo.presentation.theme.TodooTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            name = "todos_db"
        ).fallbackToDestructiveMigration().build()
    }
    private val viewModel by viewModels<TodoViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TodoViewModel(db.dao) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodooTheme {
                TodoArround(viewModel)
            }
        }
    }
}

@Composable
fun TodoArround(viewModel: TodoViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "TodoMainScreen") {
        composable(route = "TodoMainScreen") {
            TodoScreen(viewModel = viewModel) {
                navController.navigate("Add")
            }
        }

        composable(route = "Add") {
            AddTodo(viewModel = viewModel) {
                navController.navigate("TodoMainScreen")
            }
        }
    }
}
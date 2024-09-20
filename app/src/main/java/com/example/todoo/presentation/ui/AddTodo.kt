package com.example.todoo.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoo.data.TodoEntity
import com.example.todoo.presentation.TodoModelView.TodoViewModel

@Composable
fun AddTodo(viewModel: TodoViewModel,onSaveAction:()->Unit) {
    var todoTitle by remember { mutableStateOf("") }
    var subtask by remember { mutableStateOf("") }
    var subtasks by remember { mutableStateOf(listOf<String>()) }
    var showAlert by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input for the main task title
        OutlinedTextField(
            value = todoTitle,
            onValueChange = { todoTitle = it },
            label = { Text(text = "Todo Title") },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input for adding a subtask
        OutlinedTextField(
            value = subtask,
            onValueChange = { subtask = it },
            label = { Text(text = "Add Subtask") },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button to add subtask to the list
        Button(
            onClick = {
                if (subtask.isNotBlank()) {
                    subtasks = subtasks + subtask
                    subtask = "" // Clear the input field
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Subtask")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of added subtasks
        Text(text = "Subtasks:", style = MaterialTheme.typography.bodySmall)
        subtasks.forEach { task ->
            Text(text = task, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show alert if any field is missing
        if (showAlert) {
            Text(
                text = "Missing required fields",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                if (todoTitle.isBlank() || subtasks.isEmpty()) {
                    showAlert = true
                } else {
                    viewModel.insertTodo(TodoEntity(title = todoTitle, listOfTasks = subtasks, listOfTasksStatues = List(subtasks.size){false}))
                    showAlert = false // Reset alert if insertion is successful
                    onSaveAction()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save Todo")
        }
    }
}


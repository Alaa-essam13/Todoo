package com.example.todoo.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.R
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoo.data.TodoEntity
import com.example.todoo.presentation.TodoModelView.TodoViewModel
import com.example.todoo.presentation.theme.Completed
import com.example.todoo.presentation.theme.Inprogress

@Composable
fun TodoScreen(modifier: Modifier = Modifier, viewModel: TodoViewModel, onAddClick: () -> Unit) {
    val todos by viewModel.todos.collectAsState(initial = emptyList())
    TodoList(
        todos = todos,
        viewModel = viewModel,
        modifier = modifier,
        onAddClick = { onAddClick() })
}

@Composable
fun TodoList(
    todos: List<TodoEntity>,
    viewModel: TodoViewModel,
    modifier: Modifier,
    onAddClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(todos) { todo ->
                TodoListItem(todo = todo, onDelete = {
                    viewModel.deleteTodo(todo)
                }, onUpdateState = {
                    val newstate = todo.listOfTasksStatues.toMutableList()
                    newstate[it] = !newstate[it]
                    viewModel.updateTodo(todo, newstate)
                })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        FloatingActionButton(
            onClick = { onAddClick() }, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 30.dp)
                .padding(15.dp)
        ) {
            Text(text = "+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TodoListItem(todo: TodoEntity, onDelete: () -> Unit, onUpdateState: (Int) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(if(todo.listOfTasksStatues.all{it}) Inprogress else Completed) // Softer border color
            .padding(16.dp)

    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = todo.title,
                modifier = Modifier.weight(1f), // Removed weight from delete button
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333), // Changed text color for better contrast
                style = MaterialTheme.typography.headlineSmall // Use a predefined style
            )
            IconButton(
                onClick = {
                    onDelete()
                },
                modifier = Modifier.size(24.dp) // Reduced icon size for a sleeker look
            ) {
                Image(
                    painter = painterResource(id = com.example.todoo.R.drawable.garbage),
                    contentDescription = "delete",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        // Display subtasks with indentation and lighter text
        todo.listOfTasks.forEachIndexed() { index, subtask ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(35.dp)
            ) {
                Text(
                    text = "• $subtask", // Added bullet points for subtasks
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f), // Indented subtasks
                    color = Color(0xFF666666), // Lighter color for subtasks
                    style = MaterialTheme.typography.bodyLarge, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(
                        id = if (todo.listOfTasksStatues[index]) com.example.todoo.R.drawable.checked2 else com.example.todoo.R.drawable.circle),
                        contentDescription = "Circle",
                        modifier = Modifier.padding(top = 10
                            .dp).clickable {
                            onUpdateState(index)
                        })

            }

        }
    }
}




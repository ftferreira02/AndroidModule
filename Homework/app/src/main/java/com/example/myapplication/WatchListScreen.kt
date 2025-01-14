package com.example.myapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WatchListApp(viewModel: WatchListViewModel) {
    val watchList by viewModel.watchList.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Watch List") }) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AddWatchItem(viewModel::addItem)
            Spacer(modifier = Modifier.height(16.dp))
            WatchList(
                watchList = watchList,
                onToggleWatched = viewModel::toggleWatched,
                onRemove = viewModel::removeItem
            )
        }
    }
}

@Composable
fun AddWatchItem(onAdd: (String) -> Unit) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        BasicTextField(
            value = textState,
            onValueChange = { textState = it },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        Button(onClick = {
            if (textState.text.isNotBlank()) {
                onAdd(textState.text)
                textState = TextFieldValue("")
            }
        }) {
            Text("Add")
        }
    }
}

@Composable
fun WatchList(watchList: List<WatchItem>, onToggleWatched: (Int) -> Unit, onRemove: (Int) -> Unit) {
    LazyColumn {
        items(watchList) { item ->
            WatchListItem(item, onToggleWatched, onRemove)
        }
    }
}

@Composable
fun WatchListItem(item: WatchItem, onToggleWatched: (Int) -> Unit, onRemove: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(item.title, style = MaterialTheme.typography.headlineLarge)
                Text(if (item.watched) "Watched" else "Not Watched", color = Color.Gray)
            }
            Row {
                IconButton(onClick = { onToggleWatched(item.id) }) {
                    Icon(
                        imageVector = if (item.watched) Icons.Default.Check else Icons.Default.Clear,
                        contentDescription = "Toggle Watched"
                    )
                }
                IconButton(onClick = { onRemove(item.id) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
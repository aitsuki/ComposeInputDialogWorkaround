package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.component.WorkaroundDialog
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    var showSimpleDialog by remember { mutableStateOf(false) }
    var showListDialog by remember { mutableStateOf(false) }
    var showInputDialog by remember { mutableStateOf(false) }
    var showListInputDialog by remember { mutableStateOf(false) }

    if (showSimpleDialog) {
        WorkaroundDialog(onDismissRequest = { showSimpleDialog = false }) {
            Surface(shape = shapes.extraLarge) {
                Text(
                    text = "Simple Dialog",
                    style = typography.displaySmall,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }

    if (showListDialog) {
        WorkaroundDialog(onDismissRequest = { showListDialog = false }) {
            Surface(shape = shapes.extraLarge) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(100) { index ->
                        Text(text = "Item ${index + 1}")
                    }
                }
            }
        }
    }

    if (showInputDialog) {
        WorkaroundDialog(onDismissRequest = { showInputDialog = false }) {
            Surface(shape = shapes.extraLarge) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    var value by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = value,
                        onValueChange = { value = it },
                        modifier = Modifier.weight(1f, false)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SimpleButton(text = "Confirm", modifier = Modifier.align(Alignment.End)) {
                        showInputDialog = false
                    }
                }
            }
        }
    }

    if (showListInputDialog) {
        WorkaroundDialog(onDismissRequest = { showListInputDialog = false }) {
            Surface(shape = shapes.extraLarge) {
                Column {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(100) { index ->
                            Text(text = "Item ${index + 1}")
                        }
                    }
                    var value by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = value,
                        onValueChange = { value = it },
                        maxLines = 5,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    SimpleButton(
                        text = "Confirm",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        showListInputDialog = false
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SimpleButton(text = "Show simple dialog") { showSimpleDialog = true }
        SimpleButton(text = "Show list dialog") { showListDialog = true }
        SimpleButton(text = "Show input dialog") { showInputDialog = true }
        SimpleButton(text = "Show list input dialog") { showListInputDialog = true }
    }
}

@Composable
private fun SimpleButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text)
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold {
                    HomeContent(
                        modifier = Modifier.padding(it),
                    )
                }
            }
        }
    }
}
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                var showLongDialog by remember { mutableStateOf(false) }
                var showWorkaroundLongDialog by remember { mutableStateOf(false) }
                var showDialog by remember { mutableStateOf(false) }
                var showWorkaroundDialog by remember { mutableStateOf(false) }

                if (showLongDialog) {
                    InputDialog(
                        onCancel = { showLongDialog = false },
                        onConfirm = { showLongDialog = false }
                    ) {
                        repeat(4) {
                            Text(
                                text = "Some Content", modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }

                if (showWorkaroundLongDialog) {
                    InputDialogWorkaround(
                        onCancel = { showWorkaroundLongDialog = false },
                        onConfirm = { showWorkaroundLongDialog = false }
                    ) {
                        repeat(4) {
                            Text(
                                text = "Some Content", modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }

                if (showDialog) {
                    InputDialog(
                        onCancel = { showDialog = false },
                        onConfirm = { showDialog = false },
                        content = {}
                    )
                }

                if (showWorkaroundDialog) {
                    InputDialogWorkaround(
                        onCancel = { showWorkaroundDialog = false },
                        onConfirm = { showWorkaroundDialog = false },
                        content = {}
                    )
                }

                Scaffold {
                    Column(modifier = Modifier.padding(it)) {
                        Button(onClick = { showLongDialog = true }) {
                            Text(text = "Show Long Dialog")
                        }

                        Button(onClick = { showWorkaroundLongDialog = true }) {
                            Text(text = "Show Workaround Long Dialog")
                        }

                        Button(onClick = { showDialog = true }) {
                            Text(text = "Show Dialog")
                        }

                        Button(onClick = { showWorkaroundDialog = true }) {
                            Text(text = "Show Workaround Dialog")
                        }
                    }
                }
            }
        }
    }
}
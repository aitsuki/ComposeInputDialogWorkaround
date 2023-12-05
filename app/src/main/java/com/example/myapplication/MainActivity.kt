package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.component.CommonDialog
import com.example.myapplication.ui.component.FullscreenDialog
import com.example.myapplication.ui.component.MyBottomDialog
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    showNativeDialog: () -> Unit,
) {

    var showCommonDialog by remember { mutableStateOf(false) }
    if (showCommonDialog) {
        CommonDialog(title = "CommonDialog",
            onDismissRequest = { showCommonDialog = false }
        ) {
            Text(text = "Hello\n".repeat(5), modifier = Modifier.padding(16.dp))
        }
    }

    var showCommonInputDialog by remember { mutableStateOf(false) }
    if (showCommonInputDialog) {
        CommonDialog(
            title = "CommonInputDialog",
            onDismissRequest = { showCommonInputDialog = false }
        ) {
            var value by remember { mutableStateOf("") }
            Text(text = "Hello\n".repeat(5), modifier = Modifier.padding(16.dp))
            TextField(
                value = value,
                onValueChange = { value = it },
                modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }

    var showBottomDialog by remember { mutableStateOf(false) }
    if (showBottomDialog) {
        MyBottomDialog(
            title = "BottomDialog",
            onDismissRequest = { showBottomDialog = false },
        ) {
            Text(text = "Hello\n".repeat(5), modifier = Modifier.padding(16.dp))
        }
    }

    var showBottomInputDialog by remember { mutableStateOf(false) }
    if (showBottomInputDialog) {
        MyBottomDialog(
            title = "BottomDialog",
            onDismissRequest = { showBottomInputDialog = false },
        ) {
            var value by remember { mutableStateOf("") }
            Text(text = "Hello\n".repeat(5), modifier = Modifier.padding(16.dp))
            TextField(
                value = value,
                onValueChange = { value = it },
                modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }

    var showFullScreenDialog by remember { mutableStateOf(false) }
    if (showFullScreenDialog) {
        FullscreenDialog {
            showFullScreenDialog = false
        }
    }


    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(onClick = showNativeDialog, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Native Dialog")
        }
        Button(onClick = { showCommonDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Common Dialog")
        }
        Button(onClick = { showCommonInputDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Common Input Dialog")
        }
        Button(onClick = { showBottomDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Bottom Dialog")
        }
        Button(onClick = { showBottomInputDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Bottom Input Dialog")
        }
        Button(onClick = { showFullScreenDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Full screen dialog")
        }
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
                        showNativeDialog = this::showNativeDialog,
                    )
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showNativeDialog() {
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val text = TextView(this)
        text.text = "Hello\n".repeat(5)
        val textField = EditText(this)
        linearLayout.addView(text)
        linearLayout.addView(textField)

        AlertDialog.Builder(this)
            .setTitle("NativeDialog")
            .setView(linearLayout)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Confirm", null)
            .show()
    }
}
package com.example.myapplication

import android.view.Gravity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider

@Composable
fun InputDialogWorkaround(
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    var maxHeight by remember { mutableStateOf(0.dp) }
    Box(modifier = Modifier
        .fillMaxSize()
        .imePadding()
        .onSizeChanged {
            with(density) {
                maxHeight = it.height.toDp()
            }
        })

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        val window = (LocalView.current.parent as DialogWindowProvider).window
        window.setGravity(Gravity.BOTTOM)

        var value by remember { mutableStateOf("") }


        Surface(
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier.heightIn(0.dp, maxHeight)
        ) {
            Column(modifier = Modifier) {
                Text(
                    text = "What's your name?",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .wrapContentSize()
                )
                Divider()
                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                ) {
                    content()
                    OutlinedTextField(
                        value = value,
                        onValueChange = { value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                Divider()
                Button(
                    onClick = { onConfirm(value) },
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}

@Preview
@Composable
private fun InputDialogPreview() {

}
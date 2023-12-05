package com.example.myapplication.ui.component

import android.view.Gravity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider

@Composable
fun MyBottomDialog(
    title: String,
    onDismissRequest: () -> Unit,
    shape: Shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    MyDialog(onDismissRequest = onDismissRequest) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)
        Box(modifier = Modifier.myClickable { onDismissRequest() }) {
            Surface(
                shape = shape,
                modifier = Modifier
                    .fillMaxWidth()
                    .myClickable()
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    Divider()
                    Column(
                        Modifier
                            .weight(1f, false)
                            .verticalScroll(rememberScrollState())
                            .padding(contentPadding)
                    ) {
                        content()
                    }
                    Divider()
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        OutlinedButton(onClick = onDismissRequest, modifier = Modifier.weight(1f)) {
                            Text(text = "Cancel")
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(onClick = onDismissRequest, modifier = Modifier.weight(1f)) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        }
    }
}
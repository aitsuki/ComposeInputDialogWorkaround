package com.example.myapplication.ui.component

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
import androidx.compose.ui.unit.dp

@Composable
fun FakeCommonDialog(
    title: String,
    onDismissRequest: () -> Unit,
    shape: Shape = RoundedCornerShape(32.dp),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    FakeDialog(onDismissRequest = onDismissRequest) {
        Surface(shape = shape) {
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
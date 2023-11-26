package com.example.myapplication.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged

private const val TAG = "MyTextField"

@Composable
fun MyTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    forceShowError: Boolean = false,
    errorText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var isFocusDirty by rememberSaveable { mutableStateOf(false) }
    var displayErrors by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (it.isFocused && !isFocusDirty) {
                    isFocusDirty = true
                    onValueChange(value) // 触发校验
                } else if (isFocusDirty && !it.isFocused) {
                    displayErrors = true
                }
            },
        enabled = enabled,
        readOnly = readOnly,
        isError = isError && (displayErrors || forceShowError),
        supportingText = {
            if (isError && (displayErrors || forceShowError)) {
                Text(text = errorText)
            }
        },
        label = { Text(text = label) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
    )
}
package com.example.myapplication.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.myClickable(
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) = composed {
    Modifier.clickable(
        enabled = enabled,
        onClick = onClick,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
}
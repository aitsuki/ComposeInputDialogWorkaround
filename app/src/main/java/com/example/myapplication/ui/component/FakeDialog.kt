package com.example.myapplication.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun FakeDialog(
    onDismissRequest: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    contentAlignment: Alignment = Alignment.Center,
    fillMaxWidth: Boolean = false,
    content: @Composable () -> Unit
) {
    LocalFocusManager.current.clearFocus()
    BackHandler {
        if (dismissOnBackPress) {
            onDismissRequest()
        }
    }
    val dimColor = remember { Animatable(Color.Black.copy(alpha = 0f)) }
    LaunchedEffect(Unit) {
        dimColor.animateTo(Color.Black.copy(alpha = 0.6f), animationSpec = tween(220))
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = dimColor.value)
            .myClickable {
                if (dismissOnClickOutside) {
                    onDismissRequest()
                }
            },
        contentAlignment = contentAlignment,
    ) {
        val contentMaxWidth = maxWidth * if (fillMaxWidth) 1f else 0.8f
        Box(
            modifier = Modifier
                .width(contentMaxWidth)
                .myClickable()
        ) {
            content()
        }
    }
}
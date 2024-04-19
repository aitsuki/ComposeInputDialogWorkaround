package com.example.myapplication.ui.component

import android.content.Context
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentDialog
import androidx.activity.addCallback
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties

@Composable
fun WorkaroundDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val dialog = remember {
        WorkaroundDialogWrapper(
            context = context,
            properties = properties,
            onDismissRequest = onDismissRequest,
            content = content,
        )
    }

    DisposableEffect(Unit) {
        dialog.show()
        onDispose {
            dialog.dismiss()
        }
    }
}

private class WorkaroundDialogWrapper(
    context: Context,
    val onDismissRequest: () -> Unit,
    val properties: DialogProperties,
    content: @Composable () -> Unit,
) : ComponentDialog(context) {

    init {
        val window = window ?: error("Dialog has no window")
        window.requestFeature(Window.FEATURE_NO_TITLE)
        @Suppress("DEPRECATION")
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        val existingComposeView = window.decorView
            .findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0) as? ComposeView

        if (existingComposeView != null) with(existingComposeView) {
            setContent(content)
        } else ComposeView(window.context).apply {
            setContent(content)
            setContentView(this)
        }
        if (properties.usePlatformDefaultWidth) {
            window.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        } else {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        onBackPressedDispatcher.addCallback(this) {
            if (properties.dismissOnBackPress) {
                onDismissRequest()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = super.onTouchEvent(event)
        if (result && properties.dismissOnClickOutside) {
            onDismissRequest()
        }
        return result
    }
}


package com.example.myapplication.ui.component

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentDialog
import androidx.activity.addCallback
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewRootForInspector
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.window.DialogWindowProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import java.util.UUID

@Composable
fun MyDialog(
    onDismissRequest: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val composition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    val dialogId = rememberSaveable { UUID.randomUUID() }
    Log.d("MyDialog", "MyDialog ~")
    var maxWidth by remember { mutableIntStateOf(view.width) }
    var maxHeight by remember { mutableIntStateOf(view.height) }

    val dialog = remember(view) {
        MyDialogWrapper(
            onDismissRequest,
            dismissOnBackPress,
            dismissOnClickOutside,
            view,
            dialogId
        ).apply {
            Log.d("MyDialog", "MyDialogWrapper ~")
            setContent(composition) {
                Log.d("MyDialog", "setContent ~")
                Layout(content = currentContent) { measurables, _ ->
                    val constraints = Constraints(0, maxWidth, 0, maxHeight)
                    val placeables = measurables.map { it.measure(constraints) }
                    val width = placeables.maxByOrNull { it.width }?.width ?: constraints.minWidth
                    val height =
                        placeables.maxByOrNull { it.height }?.height ?: constraints.minHeight
                    Log.d("MyDialog", "width: $width, height: $height")
                    layout(width, height) {
                        placeables.forEach { it.placeRelative(0, 0) }
                    }
                }
            }
        }
    }

    DisposableEffect(view) {
        val listener = OnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            maxHeight = view.height
            maxWidth = view.width
        }
        view.addOnLayoutChangeListener(listener)
        onDispose {
            view.removeOnLayoutChangeListener(listener)
        }
    }

    DisposableEffect(dialog) {
        dialog.show()

        onDispose {
            dialog.dismiss()
            dialog.disposeComposition()
        }
    }

    SideEffect {
        dialog.updateParameters(
            onDismissRequest = onDismissRequest,
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
        )
    }
}


class MyDialogWrapper(
    private var onDismissRequest: () -> Unit,
    var dismissOnBackPress: Boolean = true,
    var dismissOnClickOutside: Boolean = true,
    composeView: View,
    dialogId: UUID
) : ComponentDialog(composeView.context), ViewRootForInspector {

    private val dialogLayout: MyDialogLayout

    override val subCompositionView: AbstractComposeView get() = dialogLayout

    init {
        val window = window ?: error("Dialog has no window")
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        dialogLayout = MyDialogLayout(context, window).apply {
            setTag(androidx.compose.ui.R.id.compose_view_saveable_id_tag, "Dialog:$dialogId")
        }
        setContentView(dialogLayout)
        dialogLayout.setViewTreeLifecycleOwner(composeView.findViewTreeLifecycleOwner())
        dialogLayout.setViewTreeViewModelStoreOwner(composeView.findViewTreeViewModelStoreOwner())
        dialogLayout.setViewTreeSavedStateRegistryOwner(
            composeView.findViewTreeSavedStateRegistryOwner()
        )
        updateParameters(onDismissRequest, dismissOnBackPress, dismissOnClickOutside)
        onBackPressedDispatcher.addCallback(this) {
            if (dismissOnBackPress) {
                onDismissRequest()
            }
        }
    }


    fun setContent(parentComposition: CompositionContext, children: @Composable () -> Unit) {
        dialogLayout.setContent(parentComposition, children)
    }

    fun updateParameters(
        onDismissRequest: () -> Unit,
        dismissOnBackPress: Boolean = true,
        dismissOnClickOutside: Boolean = true,
    ) {
        this.onDismissRequest = onDismissRequest
        this.dismissOnBackPress = dismissOnBackPress
        this.dismissOnClickOutside = dismissOnClickOutside
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    fun disposeComposition() {
        dialogLayout.disposeComposition()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = super.onTouchEvent(event)
        if (result && dismissOnClickOutside) {
            onDismissRequest()
        }
        return result
    }

    override fun cancel() {
        // Prevents the dialog from dismissing itself
        return
    }
}

@Suppress("ViewConstructor")
private class MyDialogLayout(
    context: Context,
    override val window: Window
) : AbstractComposeView(context), DialogWindowProvider {

    private var content: @Composable () -> Unit by mutableStateOf({})

    override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    fun setContent(parent: CompositionContext, content: @Composable () -> Unit) {
        setParentCompositionContext(parent)
        this.content = content
        shouldCreateCompositionOnAttachedToWindow = true
        createComposition()
    }

    @Composable
    override fun Content() {
        content()
    }
}
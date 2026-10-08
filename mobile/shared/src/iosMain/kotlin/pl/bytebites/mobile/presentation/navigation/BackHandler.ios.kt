package pl.bytebites.mobile.presentation.navigation

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

private val EDGE_SWIPE_WIDTH = 24.dp
private const val SWIPE_THRESHOLD_PX = 80f

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    if (!enabled) return

    var totalDragX by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(EDGE_SWIPE_WIDTH)
                .pointerInput(enabled) {
                    detectHorizontalDragGestures(
                        onDragStart = { totalDragX = 0f },
                        onDragEnd = {
                            if (totalDragX > SWIPE_THRESHOLD_PX) {
                                onBack()
                            }
                            totalDragX = 0f
                        },
                        onDragCancel = { totalDragX = 0f },
                        onHorizontalDrag = { _, dragAmount ->
                            if (dragAmount > 0) {
                                totalDragX += dragAmount
                            }
                        }
                    )
                }
        )
    }
}

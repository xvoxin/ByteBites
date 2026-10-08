package pl.bytebites.mobile.presentation.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier

@Composable
fun NavDisplay(
    backStack: SnapshotStateList<NavKey>,
    modifier: Modifier = Modifier,
    content: @Composable (NavKey) -> Unit
) {
    val currentKey = backStack.lastOrNull() ?: NavKey.Feed

    AnimatedContent(
        targetState = currentKey,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        modifier = modifier
    ) { key ->
        Box {
            content(key)
        }
    }
}

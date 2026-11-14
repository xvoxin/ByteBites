package pl.bytebites.mobile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.browser.window
import org.w3c.dom.events.Event

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    if (!enabled) return

    DisposableEffect(enabled) {
        window.history.pushState(null, "")
        val listener: (Event) -> Unit = {
            onBack()
        }
        window.addEventListener("popstate", listener)
        onDispose {
            window.removeEventListener("popstate", listener)
        }
    }
}

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.koin.core.context.GlobalContext
import pl.bytebites.mobile.App
import pl.bytebites.mobile.presentation.di.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    if (GlobalContext.getOrNull() == null) {
        initKoin()
    }
    val body = document.body ?: return
    ComposeViewport(body) {
        App()
    }
}

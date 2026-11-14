package pl.bytebites.mobile

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.GlobalContext
import pl.bytebites.mobile.presentation.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        if (GlobalContext.getOrNull() == null) {
            initKoin()
        }
    }
) {
    App()
}

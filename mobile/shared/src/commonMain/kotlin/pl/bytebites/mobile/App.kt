package pl.bytebites.mobile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pl.bytebites.mobile.presentation.navigation.AppNavigation
import pl.bytebites.mobile.presentation.theme.CookbookTheme

@Composable
@Preview
fun App() {

    CookbookTheme {
        AppNavigation()
    }

}

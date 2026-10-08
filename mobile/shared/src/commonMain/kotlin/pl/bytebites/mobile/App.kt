package pl.bytebites.mobile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import pl.bytebites.mobile.data.repository.di.repositoryModule
import pl.bytebites.mobile.domain.di.domainModule
import pl.bytebites.mobile.presentation.di.presentationModule
import pl.bytebites.mobile.presentation.navigation.AppNavigation
import pl.bytebites.mobile.presentation.theme.CookbookTheme

@Composable
@Preview
fun App() {

    KoinApplication(
        application = {
            modules(
                domainModule,
                repositoryModule,
                presentationModule
            )
        }
    ) {
        CookbookTheme {
            AppNavigation()
        }
    }

}

package pl.bytebites.mobile.domain.di

import org.koin.dsl.module
import pl.bytebites.mobile.domain.auth.UserAuthenticator
import pl.bytebites.mobile.domain.creator.RecipeCreator
import pl.bytebites.mobile.domain.deleter.RecipeDeleter
import pl.bytebites.mobile.domain.provider.RecipeProvider
import pl.bytebites.mobile.domain.provider.UserProvider
import pl.bytebites.mobile.domain.toggler.RecipeFavoriteToggler

val domainModule = module {
    factory { RecipeProvider(get()) }
    factory { RecipeCreator(get()) }
    factory { RecipeDeleter(get()) }
    factory { RecipeFavoriteToggler(get()) }
    factory { UserProvider(get()) }
    factory { UserAuthenticator(get()) }
}

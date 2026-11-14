package pl.bytebites.mobile.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import pl.bytebites.mobile.domain.recipe.RecipeDetailsProvider
import pl.bytebites.mobile.domain.recipe.RecipeFeedProvider
import pl.bytebites.mobile.domain.recipe.RepositoryRecipeDetailsProvider
import pl.bytebites.mobile.domain.recipe.RepositoryRecipeFeedProvider
import pl.bytebites.mobile.domain.user.CurrentUserProvider
import pl.bytebites.mobile.domain.user.RepositoryCurrentUserProvider
import pl.bytebites.mobile.domain.user.SessionTerminationProcess
import pl.bytebites.mobile.domain.user.UserLogoutProcess

val domainModule = module {

    factoryOf(::RepositoryRecipeFeedProvider) bind RecipeFeedProvider::class
    factoryOf(::RepositoryRecipeDetailsProvider) bind RecipeDetailsProvider::class
    factoryOf(::RepositoryCurrentUserProvider) bind CurrentUserProvider::class
    factoryOf(::SessionTerminationProcess) bind UserLogoutProcess::class

}

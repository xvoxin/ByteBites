package pl.bytebites.mobile.data.repository.di

import org.koin.dsl.module
import pl.bytebites.mobile.data.repository.InMemoryAuthRepository
import pl.bytebites.mobile.data.repository.InMemoryRecipeRepository
import pl.bytebites.mobile.domain.repository.AuthRepository
import pl.bytebites.mobile.domain.repository.RecipeRepository

val repositoryModule = module {
    single<RecipeRepository> { InMemoryRecipeRepository() }
    single<AuthRepository> { InMemoryAuthRepository() }
}

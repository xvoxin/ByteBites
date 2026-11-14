package pl.bytebites.mobile.presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import pl.bytebites.mobile.presentation.screen.feed.interactor.FeedProvider
import pl.bytebites.mobile.presentation.screen.feed.interactor.FeedProviderInteractor
import pl.bytebites.mobile.presentation.screen.feed.viewmodel.FeedScreenViewModel
import pl.bytebites.mobile.presentation.screen.feed.viewmodel.FeedViewModel
import pl.bytebites.mobile.presentation.screen.profile.interactor.ProfileProvider
import pl.bytebites.mobile.presentation.screen.profile.interactor.ProfileProviderInteractor
import pl.bytebites.mobile.presentation.screen.profile.viewmodel.ProfileScreenViewModel
import pl.bytebites.mobile.presentation.screen.profile.viewmodel.ProfileViewModel
import pl.bytebites.mobile.presentation.screen.recipe_detail.interactor.RecipeDetailProvider
import pl.bytebites.mobile.presentation.screen.recipe_detail.interactor.RecipeDetailProviderInteractor
import pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel.RecipeDetailScreenViewModel
import pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel.RecipeDetailViewModel

val presentationModule = module {

    factoryOf(::FeedProviderInteractor) bind FeedProvider::class
    factoryOf(::RecipeDetailProviderInteractor) bind RecipeDetailProvider::class
    factoryOf(::ProfileProviderInteractor) bind ProfileProvider::class

    viewModelOf(::FeedScreenViewModel) bind FeedViewModel::class
    viewModelOf(::RecipeDetailScreenViewModel) bind RecipeDetailViewModel::class
    viewModelOf(::ProfileScreenViewModel) bind ProfileViewModel::class

}

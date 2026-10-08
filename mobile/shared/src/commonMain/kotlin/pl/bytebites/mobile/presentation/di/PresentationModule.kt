package pl.bytebites.mobile.presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import pl.bytebites.mobile.presentation.feed.interactor.FeedProvider
import pl.bytebites.mobile.presentation.feed.interactor.FeedProviderInteractor
import pl.bytebites.mobile.presentation.feed.viewmodel.FeedScreenViewModel
import pl.bytebites.mobile.presentation.feed.viewmodel.FeedViewModel
import pl.bytebites.mobile.presentation.navigation.AppNavigator
import pl.bytebites.mobile.presentation.recipe_detail.interactor.RecipeDetailProvider
import pl.bytebites.mobile.presentation.recipe_detail.interactor.RecipeDetailProviderInteractor
import pl.bytebites.mobile.presentation.recipe_detail.viewmodel.RecipeDetailScreenViewModel
import pl.bytebites.mobile.presentation.recipe_detail.viewmodel.RecipeDetailViewModel

val presentationModule = module {

    single { AppNavigator() }

    factoryOf(::FeedProviderInteractor) bind FeedProvider::class
    factoryOf(::RecipeDetailProviderInteractor) bind RecipeDetailProvider::class

    viewModelOf(::FeedScreenViewModel) bind FeedViewModel::class
    viewModelOf(::RecipeDetailScreenViewModel) bind RecipeDetailViewModel::class

}

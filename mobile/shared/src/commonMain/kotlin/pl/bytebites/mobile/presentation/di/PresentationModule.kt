package pl.bytebites.mobile.presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import pl.bytebites.mobile.presentation.feed.interactor.FeedProvider
import pl.bytebites.mobile.presentation.feed.interactor.FeedProviderInteractor
import pl.bytebites.mobile.presentation.feed.viewmodel.FeedScreenViewModel
import pl.bytebites.mobile.presentation.feed.viewmodel.FeedViewModel

val presentationModule = module {

    factoryOf(::FeedProviderInteractor) bind FeedProvider::class

    viewModelOf(::FeedScreenViewModel) bind FeedViewModel::class

}

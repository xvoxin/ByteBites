package pl.bytebites.mobile.presentation.screen.feed.interactor

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.presentation.screen.feed.model.Recipe
import pl.bytebites.mobile.presentation.state.QueryState

typealias FeedProvidingState = QueryState<List<Recipe>>

interface FeedProvider {

    fun provideRecipes(): Flow<FeedProvidingState>

}

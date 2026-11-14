package pl.bytebites.mobile.presentation.screen.feed.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.domain.recipe.RecipeFeedProvider
import pl.bytebites.mobile.domain.state.QueryState as DomainQueryState
import pl.bytebites.mobile.presentation.screen.feed.model.toPresentation
import pl.bytebites.mobile.presentation.state.QueryState

class FeedProviderInteractor(
    private val recipeFeedProvider: RecipeFeedProvider
) : FeedProvider {

    override fun provideRecipes(): Flow<FeedProvidingState> =
        recipeFeedProvider.provide().map { state ->
            when (state) {
                is DomainQueryState.Value -> QueryState.Value(state.value.map { it.toPresentation() })
                is DomainQueryState.Error -> QueryState.Error(state.throwable)
            }
        }

}

package pl.bytebites.mobile.presentation.screen.feed.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.bytebites.mobile.presentation.error.DefaultErrorHandler
import pl.bytebites.mobile.presentation.error.ErrorHandler
import pl.bytebites.mobile.presentation.screen.feed.interactor.FeedProvider
import pl.bytebites.mobile.presentation.screen.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.screen.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.screen.feed.model.FeedState
import pl.bytebites.mobile.presentation.screen.feed.model.Recipe
import pl.bytebites.mobile.presentation.state.QueryState

class FeedScreenViewModel(
    private val feedProvider: FeedProvider,
    errorHandler: ErrorHandler = DefaultErrorHandler()
) : FeedViewModel(), ErrorHandler by errorHandler {

    override val state = MutableStateFlow(FeedState(isLoading = true))

    private val effectChannel = Channel<FeedEffect>(Channel.BUFFERED)

    override val effect: Flow<FeedEffect> = effectChannel.receiveAsFlow()

    init {
        provideRecipes()
    }

    override fun onIntent(intent: FeedIntent) {
        when (intent) {
            is FeedIntent.RecipeClicked -> {
                viewModelScope.launch {
                    effectChannel.send(FeedEffect.NavigateToDetail(intent.recipeId))
                }
            }

            is FeedIntent.ProfileClicked -> {
                viewModelScope.launch {
                    effectChannel.send(FeedEffect.NavigateToProfile)
                }
            }
        }
    }

    private fun provideRecipes() {
        viewModelScope.launch {
            feedProvider.provideRecipes().collect { queryState ->
                when (queryState) {
                    is QueryState.Value -> onRecipesProvided(queryState.value)
                    is QueryState.Error -> onProvidingError(queryState.throwable)
                }
            }
        }
    }

    private fun onRecipesProvided(recipes: List<Recipe>) {
        state.update {
            it.copy(
                recipes = recipes,
                isLoading = false,
                error = null
            )
        }
    }

    private fun onProvidingError(throwable: Throwable) {
        showError(throwable)
        state.update { it.copy(isLoading = false) }
    }

}

package pl.bytebites.mobile.presentation.feed.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.bytebites.mobile.presentation.feed.interactor.FeedProvider
import pl.bytebites.mobile.presentation.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.feed.model.FeedState

class FeedScreenViewModel(
    private val feedProvider: FeedProvider
) : FeedViewModel() {

    override val state = MutableStateFlow(FeedState(isLoading = true))

    private val effectChannel = Channel<FeedEffect>(Channel.BUFFERED)
    override val effect: Flow<FeedEffect> = effectChannel.receiveAsFlow()

    init {
        loadRecipes()
    }

    override fun onIntent(intent: FeedIntent) {
        when (intent) {
            is FeedIntent.LoadRecipes -> loadRecipes()
            is FeedIntent.RecipeClicked -> {
                viewModelScope.launch {
                    effectChannel.send(FeedEffect.NavigateToDetail(intent.recipeId))
                }
            }
        }
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true, error = null) }
            feedProvider.getRecipes().collect { recipeList ->
                state.update {
                    it.copy(
                        recipes = recipeList,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

}

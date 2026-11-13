package pl.bytebites.mobile.presentation.screen.feed.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.bytebites.mobile.presentation.screen.feed.interactor.FeedProvider
import pl.bytebites.mobile.presentation.screen.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.screen.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.screen.feed.model.FeedState

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

    private fun loadRecipes() {
        viewModelScope.launch {
            feedProvider.getRecipes().collect { recipes ->
                state.update {
                    it.copy(
                        recipes = recipes,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

}

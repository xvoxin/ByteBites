package pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.bytebites.mobile.presentation.screen.recipe_detail.interactor.RecipeDetailProvider
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailEffect
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailIntent
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailState

class RecipeDetailScreenViewModel(
    private val recipeDetailProvider: RecipeDetailProvider
) : RecipeDetailViewModel() {

    override val state = MutableStateFlow(RecipeDetailState(isLoading = true))

    private val effectChannel = Channel<RecipeDetailEffect>(Channel.BUFFERED)
    override val effect: Flow<RecipeDetailEffect> = effectChannel.receiveAsFlow()

    override fun onIntent(intent: RecipeDetailIntent) {
        when (intent) {
            is RecipeDetailIntent.LoadRecipeDetail -> loadRecipeDetail(intent.recipeId)
            is RecipeDetailIntent.BackClicked -> {
                viewModelScope.launch {
                    effectChannel.send(RecipeDetailEffect.NavigateBack)
                }
            }
        }
    }

    private fun loadRecipeDetail(recipeId: String) {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true, error = null) }
            recipeDetailProvider.getRecipeById(recipeId).collect { detail ->
                if (detail != null) {
                    state.update {
                        it.copy(
                            recipeDetail = detail,
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    state.update {
                        it.copy(
                            isLoading = false,
                            error = "Nie znaleziono szczegółów przepisu"
                        )
                    }
                }
            }
        }
    }

}

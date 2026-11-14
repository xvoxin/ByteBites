package pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel

import androidx.lifecycle.viewModelScope
import bytebites.shared.generated.resources.Res
import bytebites.shared.generated.resources.recipe_detail_not_found
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.bytebites.mobile.presentation.error.DefaultErrorHandler
import pl.bytebites.mobile.presentation.error.ErrorHandler
import pl.bytebites.mobile.presentation.resource.StringValue
import pl.bytebites.mobile.presentation.screen.recipe_detail.interactor.RecipeDetailProvider
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetail
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailEffect
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailIntent
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailState

class RecipeDetailScreenViewModel(
    private val recipeDetailProvider: RecipeDetailProvider,
    errorHandler: ErrorHandler = DefaultErrorHandler()
) : RecipeDetailViewModel(), ErrorHandler by errorHandler {

    override val state = MutableStateFlow(RecipeDetailState(isLoading = true))

    private val effectChannel = Channel<RecipeDetailEffect>(Channel.BUFFERED)
    override val effect: Flow<RecipeDetailEffect> = effectChannel.receiveAsFlow()

    private var loadRecipeDetailJob: Job? = null

    override fun onIntent(intent: RecipeDetailIntent) {
        when (intent) {
            is RecipeDetailIntent.LoadRecipeDetail -> loadRecipeDetail(intent.recipeId)
            is RecipeDetailIntent.BackClicked -> navigateBack()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            effectChannel.send(RecipeDetailEffect.NavigateBack)
        }
    }

    private fun loadRecipeDetail(recipeId: String) {
        loadRecipeDetailJob?.cancel()
        loadRecipeDetailJob = viewModelScope.launch {
            state.update { it.copy(isLoading = true, error = null) }
            recipeDetailProvider.getRecipeById(recipeId)
                .catch { handleLoadingError(it) }
                .collect { handleRecipeDetailResult(it) }
        }
    }

    private fun handleRecipeDetailResult(detail: RecipeDetail?) {
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
                    error = StringValue.Resource(Res.string.recipe_detail_not_found)
                )
            }
        }
    }

    private fun handleLoadingError(throwable: Throwable) {
        showError(throwable)
        state.update { it.copy(isLoading = false) }
    }

}

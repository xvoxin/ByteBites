package pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import pl.bytebites.mobile.presentation.error.DefaultErrorHandler
import pl.bytebites.mobile.presentation.error.ErrorHandler
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailEffect
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailIntent
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailState

class FakeRecipeDetailViewModel(
    initialState: RecipeDetailState = RecipeDetailState(),
    errorHandler: ErrorHandler = DefaultErrorHandler()
) : RecipeDetailViewModel(), ErrorHandler by errorHandler {

    override val state = MutableStateFlow(initialState)
    override val effect: Flow<RecipeDetailEffect> = Channel<RecipeDetailEffect>().receiveAsFlow()

    override fun onIntent(intent: RecipeDetailIntent) {}

}

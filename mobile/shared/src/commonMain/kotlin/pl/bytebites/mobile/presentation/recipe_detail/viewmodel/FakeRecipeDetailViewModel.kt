package pl.bytebites.mobile.presentation.recipe_detail.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import pl.bytebites.mobile.presentation.recipe_detail.model.RecipeDetailEffect
import pl.bytebites.mobile.presentation.recipe_detail.model.RecipeDetailIntent
import pl.bytebites.mobile.presentation.recipe_detail.model.RecipeDetailState

class FakeRecipeDetailViewModel(
    initialState: RecipeDetailState = RecipeDetailState()
) : RecipeDetailViewModel() {

    override val state = MutableStateFlow(initialState)
    override val effect: Flow<RecipeDetailEffect> = Channel<RecipeDetailEffect>().receiveAsFlow()

    override fun onIntent(intent: RecipeDetailIntent) {}

}

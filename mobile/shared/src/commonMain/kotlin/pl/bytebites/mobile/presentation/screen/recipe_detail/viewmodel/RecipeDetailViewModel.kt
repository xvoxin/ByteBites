package pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailEffect
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailIntent
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailState

abstract class RecipeDetailViewModel : ViewModel() {

    abstract val state: StateFlow<RecipeDetailState>
    abstract val effect: Flow<RecipeDetailEffect>

    abstract fun onIntent(intent: RecipeDetailIntent)

}

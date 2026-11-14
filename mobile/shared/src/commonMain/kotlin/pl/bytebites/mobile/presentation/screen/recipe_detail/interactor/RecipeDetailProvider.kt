package pl.bytebites.mobile.presentation.screen.recipe_detail.interactor

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetail
import pl.bytebites.mobile.presentation.state.QueryState

typealias RecipeDetailProvidingState = QueryState<RecipeDetail?>

interface RecipeDetailProvider {

    fun provideRecipeDetail(id: String): Flow<RecipeDetailProvidingState>

}

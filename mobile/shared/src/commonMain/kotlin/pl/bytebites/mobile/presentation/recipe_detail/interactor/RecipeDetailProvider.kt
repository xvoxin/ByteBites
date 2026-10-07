package pl.bytebites.mobile.presentation.recipe_detail.interactor

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.presentation.recipe_detail.model.RecipeDetail

interface RecipeDetailProvider {

    fun getRecipeById(id: String): Flow<RecipeDetail?>

}

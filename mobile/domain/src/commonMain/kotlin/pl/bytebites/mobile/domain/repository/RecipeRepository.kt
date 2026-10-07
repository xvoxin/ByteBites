package pl.bytebites.mobile.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.domain.model.Recipe

interface RecipeRepository {
    fun getRecipes(): Flow<List<Recipe>>
    fun getFeaturedRecipe(): Flow<Recipe?>
    suspend fun toggleFavorite(recipeId: String)
    suspend fun addRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipeId: String)
}

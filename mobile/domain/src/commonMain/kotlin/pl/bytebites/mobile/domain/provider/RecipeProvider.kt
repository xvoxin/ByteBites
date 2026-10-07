package pl.bytebites.mobile.domain.provider

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.repository.RecipeRepository

class RecipeProvider(
    private val recipeRepository: RecipeRepository
) {
    fun getRecipes(): Flow<List<Recipe>> = recipeRepository.getRecipes()

    fun getFeaturedRecipe(): Flow<Recipe?> = recipeRepository.getFeaturedRecipe()

    fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeRepository.getRecipes().map { list ->
            list.filter { it.isFavorite }
        }
    }

    fun getRecipesByCategory(categoryId: String): Flow<List<Recipe>> {
        return recipeRepository.getRecipes().map { list ->
            list.filter { it.categoryId == categoryId }
        }
    }
}

package pl.bytebites.mobile.presentation.screen.feed

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.repository.RecipeRepository

class FakeRecipeRepository(
    initialRecipes: List<Recipe> = emptyList()
) : RecipeRepository {

    val recipesFlow = MutableStateFlow(initialRecipes)
    var errorToThrow: Throwable? = null

    override fun getRecipes(): Flow<List<Recipe>> = flow {
        val error = errorToThrow
        if (error != null) {
            throw error
        } else {
            emitAll(recipesFlow)
        }
    }

    override fun getFeaturedRecipe(): Flow<Recipe?> {
        return recipesFlow.map { list -> list.firstOrNull { it.isFeatured } }
    }

    override suspend fun toggleFavorite(recipeId: String) {
        recipesFlow.value = recipesFlow.value.map {
            if (it.id == recipeId) it.copy(isFavorite = !it.isFavorite) else it
        }
    }

    override suspend fun addRecipe(recipe: Recipe) {
        recipesFlow.value = listOf(recipe) + recipesFlow.value
    }

    override suspend fun deleteRecipe(recipeId: String) {
        recipesFlow.value = recipesFlow.value.filterNot { it.id == recipeId }
    }

}

package pl.bytebites.mobile.domain.provider

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.repository.RecipeRepository

class RecipeDetailsProvider(
    private val recipeRepository: RecipeRepository
) {

    fun getRecipeById(id: String): Flow<Recipe?> =
        recipeRepository.getRecipes().map { list ->
            list.find { it.id == id }
        }

}

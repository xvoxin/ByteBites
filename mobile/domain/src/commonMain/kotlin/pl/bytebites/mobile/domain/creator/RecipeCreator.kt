package pl.bytebites.mobile.domain.creator

import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.repository.RecipeRepository

class RecipeCreator(
    private val recipeRepository: RecipeRepository
) {
    suspend fun createRecipe(recipe: Recipe): Result<Unit> {
        if (recipe.title.isBlank()) {
            return Result.failure(IllegalArgumentException("Recipe title cannot be blank."))
        }
        if (recipe.ingredients.isEmpty()) {
            return Result.failure(IllegalArgumentException("Recipe must contain at least one ingredient."))
        }
        recipeRepository.addRecipe(recipe)
        return Result.success(Unit)
    }
}

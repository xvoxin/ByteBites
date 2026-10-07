package pl.bytebites.mobile.domain.deleter

import pl.bytebites.mobile.domain.repository.RecipeRepository

class RecipeDeleter(
    private val recipeRepository: RecipeRepository
) {
    suspend fun deleteRecipe(recipeId: String): Result<Unit> {
        if (recipeId.isBlank()) {
            return Result.failure(IllegalArgumentException("Invalid recipe ID."))
        }
        recipeRepository.deleteRecipe(recipeId)
        return Result.success(Unit)
    }
}

package pl.bytebites.mobile.domain.toggler

import pl.bytebites.mobile.domain.repository.RecipeRepository

class RecipeFavoriteToggler(
    private val recipeRepository: RecipeRepository
) {
    suspend fun toggleFavorite(recipeId: String): Result<Unit> {
        if (recipeId.isBlank()) {
            return Result.failure(IllegalArgumentException("Invalid recipe ID."))
        }
        recipeRepository.toggleFavorite(recipeId)
        return Result.success(Unit)
    }
}

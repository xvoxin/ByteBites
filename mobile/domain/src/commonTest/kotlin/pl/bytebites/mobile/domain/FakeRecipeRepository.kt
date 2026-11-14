package pl.bytebites.mobile.domain

import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.repository.RecipeRepository

class FakeRecipeRepository(
    initialRecipes: List<Recipe> = emptyList()
) : RecipeRepository {

    var recipes: List<Recipe> = initialRecipes
    var recipesResult: Result<List<Recipe>>? = null
    var recipeDetailResult: Result<Recipe?>? = null

    override suspend fun getRecipes(): Result<List<Recipe>> {
        return recipesResult ?: Result.success(recipes)
    }

    override suspend fun getRecipeById(id: String): Result<Recipe?> {
        return recipeDetailResult ?: Result.success(recipes.find { it.id == id })
    }

}

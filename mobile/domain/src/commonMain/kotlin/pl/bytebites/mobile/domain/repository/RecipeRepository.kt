package pl.bytebites.mobile.domain.repository

import pl.bytebites.mobile.domain.model.Recipe

interface RecipeRepository {

    suspend fun getRecipes(): Result<List<Recipe>>

    suspend fun getRecipeById(id: String): Result<Recipe?>

}

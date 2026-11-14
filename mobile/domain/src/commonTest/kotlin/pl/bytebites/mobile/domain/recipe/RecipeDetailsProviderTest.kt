package pl.bytebites.mobile.domain.recipe

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.FakeRecipeRepository
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.state.QueryState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RecipeDetailsProviderTest {

    private val fakeRecipeRepository = FakeRecipeRepository()
    private val recipeDetailsProvider = RepositoryRecipeDetailsProvider(fakeRecipeRepository)

    @Test
    fun `emit recipe details when recipe found`() = runTest {
        fakeRecipeRepository.recipeDetailResult = Result.success(sampleRecipe())

        recipeDetailsProvider.provide("1").test {
            val item = awaitItem()
            assertIs<QueryState.Value<Recipe?>>(item)
            assertEquals(sampleRecipe(), item.value)
            awaitComplete()
        }
    }

    @Test
    fun `emit null when recipe not found`() = runTest {
        fakeRecipeRepository.recipeDetailResult = Result.success(null)

        recipeDetailsProvider.provide("non_existent").test {
            val item = awaitItem()
            assertIs<QueryState.Value<Recipe?>>(item)
            assertEquals(null, item.value)
            awaitComplete()
        }
    }

    @Test
    fun `emit error state on repository failure`() = runTest {
        fakeRecipeRepository.recipeDetailResult = Result.failure(sampleError)

        recipeDetailsProvider.provide("1").test {
            val item = awaitItem()
            assertIs<QueryState.Error>(item)
            assertEquals(sampleError, item.throwable)
            awaitComplete()
        }
    }

    private val sampleError = RuntimeException("Database error")

    private fun sampleRecipe() = Recipe(
        id = "1",
        title = "Apple Pie",
        prepTime = "45 min",
        difficulty = "Medium",
        rating = 4.8,
        servings = 4,
        description = "Delicious pie",
        ingredients = listOf("Apples", "Flour"),
        steps = listOf("Bake")
    )

}

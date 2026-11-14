package pl.bytebites.mobile.domain.recipe

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.FakeRecipeRepository
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.state.QueryState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RecipeFeedProviderTest {

    private val fakeRecipeRepository = FakeRecipeRepository()
    private val recipeFeedProvider = RepositoryRecipeFeedProvider(fakeRecipeRepository)

    @Test
    fun `when repository succeeds with recipes, emit QueryState Value with recipe list`() = runTest {
        fakeRecipeRepository.recipes = sampleRecipes()

        recipeFeedProvider.provide().test {
            val item = awaitItem()
            assertIs<QueryState.Value<List<Recipe>>>(item)
            assertEquals(sampleRecipes(), item.value)
            awaitComplete()
        }
    }

    @Test
    fun `when repository succeeds with empty list, emit QueryState Value with empty list`() = runTest {
        fakeRecipeRepository.recipes = emptyList()

        recipeFeedProvider.provide().test {
            val item = awaitItem()
            assertIs<QueryState.Value<List<Recipe>>>(item)
            assertEquals(emptyList(), item.value)
            awaitComplete()
        }
    }

    @Test
    fun `when repository fails with exception, emit QueryState Error with throwable`() = runTest {
        fakeRecipeRepository.recipesResult = Result.failure(sampleError)

        recipeFeedProvider.provide().test {
            val item = awaitItem()
            assertIs<QueryState.Error>(item)
            assertEquals(sampleError, item.throwable)
            awaitComplete()
        }
    }

    private val sampleError = RuntimeException("Database error")

    private fun sampleRecipes() = listOf(
        Recipe(
            id = "1",
            title = "Szarlotka",
            prepTime = "45 min",
            difficulty = "Średnie",
            rating = 4.8,
            servings = 4,
            description = "Pyszne ciasto",
            ingredients = listOf("Jabłka", "Mąka"),
            steps = listOf("Upiecz")
        )
    )

}

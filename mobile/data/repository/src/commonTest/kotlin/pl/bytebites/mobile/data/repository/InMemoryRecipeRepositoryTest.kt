package pl.bytebites.mobile.data.repository

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.model.Recipe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InMemoryRecipeRepositoryTest {

    private val repository = InMemoryRecipeRepository()

    @Test
    fun `emit initial recipes`() = runTest {
        repository.getRecipes().test {
            val recipes = awaitItem()
            assertTrue(recipes.isNotEmpty(), "Initial recipes list should not be empty")
            assertEquals(3, recipes.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `update recipe favorite status`() = runTest {
        repository.getRecipes().test {
            val initialList = awaitItem()
            val targetRecipe = initialList.first { !it.isFavorite }

            repository.toggleFavorite(targetRecipe.id)

            val updatedList = awaitItem()
            val updatedRecipe = updatedList.first { it.id == targetRecipe.id }
            assertTrue(updatedRecipe.isFavorite, "Recipe favorite status should be true after toggling")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `prepend new recipe to list`() = runTest {
        val newRecipe = Recipe(
            id = "new_test_1",
            title = "Test Pizza",
            categoryId = "mains",
            categoryName = "Obiady",
            prepTime = "15 min",
            difficulty = "Łatwe",
            rating = 5.0,
            servings = 2,
            description = "Smaczna pizza testowa",
            ingredients = listOf("Ciasto", "Sos pomidorowy", "Ser"),
            steps = listOf("Upiecz w 250C")
        )

        repository.getRecipes().test {
            val initial = awaitItem()
            assertEquals(3, initial.size)

            repository.addRecipe(newRecipe)

            val updated = awaitItem()
            assertEquals(4, updated.size)
            assertEquals(newRecipe.id, updated.first().id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `remove recipe from list`() = runTest {
        repository.getRecipes().test {
            val initial = awaitItem()
            val targetId = initial.first().id

            repository.deleteRecipe(targetId)

            val updated = awaitItem()
            assertEquals(initial.size - 1, updated.size)
            assertFalse(updated.any { it.id == targetId })
            cancelAndIgnoreRemainingEvents()
        }
    }

}

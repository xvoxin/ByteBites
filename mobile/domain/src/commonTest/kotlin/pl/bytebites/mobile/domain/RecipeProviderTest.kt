package pl.bytebites.mobile.domain

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.provider.RecipeProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RecipeProviderTest {

    private val testRecipes = listOf(
        Recipe(
            id = "1",
            title = "Zupa",
            categoryId = "mains",
            categoryName = "Obiady",
            prepTime = "10",
            difficulty = "Łatwe",
            rating = 5.0,
            servings = 1,
            description = "",
            ingredients = listOf("a"),
            steps = listOf("b"),
            emoji = "🍲",
            isFavorite = true,
            isFeatured = true
        ),
        Recipe(
            id = "2",
            title = "Ciasto",
            categoryId = "desserts",
            categoryName = "Desery",
            prepTime = "20",
            difficulty = "Łatwe",
            rating = 4.0,
            servings = 2,
            description = "",
            ingredients = listOf("x"),
            steps = listOf("y"),
            emoji = "🍰",
            isFavorite = false,
            isFeatured = false
        )
    )

    private val repository = FakeRecipeRepository(testRecipes)
    private val provider = RecipeProvider(repository)

    @Test
    fun `filter only favorite recipes`() = runTest {
        provider.getFavoriteRecipes().test {
            val favorites = awaitItem()
            assertEquals(1, favorites.size)
            assertTrue(favorites.all { it.isFavorite })
            assertEquals("1", favorites.first().id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `filter recipes by category id`() = runTest {
        provider.getRecipesByCategory("desserts").test {
            val desserts = awaitItem()
            assertEquals(1, desserts.size)
            assertEquals("2", desserts.first().id)
            cancelAndIgnoreRemainingEvents()
        }
    }

}

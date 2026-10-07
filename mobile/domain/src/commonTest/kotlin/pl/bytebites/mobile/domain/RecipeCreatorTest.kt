package pl.bytebites.mobile.domain

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.creator.RecipeCreator
import pl.bytebites.mobile.domain.model.Recipe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RecipeCreatorTest {

    private val repository = FakeRecipeRepository()
    private val creator = RecipeCreator(repository)

    @Test
    fun createRecipe_fails_when_title_is_blank() = runTest {
        val recipe = Recipe(
            id = "1",
            title = "   ",
            categoryId = "mains",
            categoryName = "Obiady",
            prepTime = "10 min",
            difficulty = "Łatwe",
            rating = 5.0,
            servings = 2,
            description = "Opis",
            ingredients = listOf("Składnik"),
            steps = listOf("Krok"),
            emoji = "🍲"
        )

        val result = creator.createRecipe(recipe)

        assertTrue(result.isFailure, "Should fail when title is blank")
    }

    @Test
    fun createRecipe_fails_when_ingredients_are_empty() = runTest {
        val recipe = Recipe(
            id = "1",
            title = "Pyszna Zupa",
            categoryId = "mains",
            categoryName = "Obiady",
            prepTime = "10 min",
            difficulty = "Łatwe",
            rating = 5.0,
            servings = 2,
            description = "Opis",
            ingredients = emptyList(),
            steps = listOf("Krok"),
            emoji = "🍲"
        )

        val result = creator.createRecipe(recipe)

        assertTrue(result.isFailure, "Should fail when ingredients list is empty")
    }

    @Test
    fun createRecipe_succeeds_and_adds_recipe_to_repository() = runTest {
        val recipe = Recipe(
            id = "1",
            title = "Pyszna Zupa",
            categoryId = "mains",
            categoryName = "Obiady",
            prepTime = "10 min",
            difficulty = "Łatwe",
            rating = 5.0,
            servings = 2,
            description = "Opis",
            ingredients = listOf("Marchewka"),
            steps = listOf("Gotuj"),
            emoji = "🍲"
        )

        val result = creator.createRecipe(recipe)

        assertTrue(result.isSuccess)

        repository.getRecipes().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("Pyszna Zupa", list.first().title)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

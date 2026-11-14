package pl.bytebites.mobile.data.repository

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class InMemoryRecipeRepositoryTest {

    private val repository = InMemoryRecipeRepository()

    @Test
    fun `return initial recipes list`() = runTest {
        val result = repository.getRecipes()

        assertTrue(result.isSuccess)
        val recipes = result.getOrThrow()
        assertEquals(3, recipes.size)
        assertEquals("1", recipes.first().id)
    }

    @Test
    fun `return recipe for existing id`() = runTest {
        val result = repository.getRecipeById("1")

        assertTrue(result.isSuccess)
        val recipe = result.getOrThrow()
        assertNotNull(recipe)
        assertEquals("1", recipe.id)
        assertEquals("Kremowa Zupa Dyniowa z Imbirem", recipe.title)
    }

    @Test
    fun `return null when recipe not found`() = runTest {
        val result = repository.getRecipeById("non_existing_id")

        assertTrue(result.isSuccess)
        assertNull(result.getOrThrow())
    }

}

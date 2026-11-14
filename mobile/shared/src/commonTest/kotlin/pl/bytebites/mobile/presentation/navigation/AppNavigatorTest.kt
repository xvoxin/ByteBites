package pl.bytebites.mobile.presentation.navigation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppNavigatorTest {

    @Test
    fun `navigate to new destination appends to backstack`() {
        val navigator = AppNavigator()
        assertEquals(1, navigator.backStack.size)
        assertEquals(NavKey.Feed, navigator.backStack.last())

        navigator.navigateTo(NavKey.RecipeDetail("123"))

        assertEquals(2, navigator.backStack.size)
        assertEquals(NavKey.RecipeDetail("123"), navigator.backStack.last())
    }

    @Test
    fun `navigate back pops destination from backstack`() {
        val navigator = AppNavigator()
        navigator.navigateTo(NavKey.Profile)
        assertEquals(2, navigator.backStack.size)

        val result = navigator.navigateBack()

        assertTrue(result, "navigateBack should return true when backstack had more than 1 item")
        assertEquals(1, navigator.backStack.size)
        assertEquals(NavKey.Feed, navigator.backStack.last())
    }

    @Test
    fun `navigate back when on root destination returns false and does not pop`() {
        val navigator = AppNavigator()
        assertEquals(1, navigator.backStack.size)

        val result = navigator.navigateBack()

        assertFalse(result, "navigateBack should return false when on root destination")
        assertEquals(1, navigator.backStack.size)
        assertEquals(NavKey.Feed, navigator.backStack.last())
    }

    @Test
    fun `serialize and deserialize nav keys accurately`() {
        val feedRoute = NavKey.Feed.toRoute()
        val profileRoute = NavKey.Profile.toRoute()
        val detailRoute = NavKey.RecipeDetail("abc-456").toRoute()

        assertEquals("feed", feedRoute)
        assertEquals("profile", profileRoute)
        assertEquals("recipe_detail:abc-456", detailRoute)

        assertEquals(NavKey.Feed, "feed".toNavKey())
        assertEquals(NavKey.Profile, "profile".toNavKey())
        assertEquals(NavKey.RecipeDetail("abc-456"), "recipe_detail:abc-456".toNavKey())
        assertEquals(NavKey.Feed, "unknown_route".toNavKey())
    }

}

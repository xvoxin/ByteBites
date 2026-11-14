package pl.bytebites.mobile.data.repository

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class InMemoryAuthRepositoryTest {

    private val repository = InMemoryAuthRepository()

    @Test
    fun `return default user initially`() = runTest {
        val result = repository.getCurrentUser()

        assertTrue(result.isSuccess)
        val user = result.getOrThrow()
        assertNotNull(user)
        assertEquals("user_1", user.id)
    }

    @Test
    fun `update current user on login`() = runTest {
        val loginResult = repository.login("chef@bytebites.pl", "secret")
        assertTrue(loginResult.isSuccess)

        val userResult = repository.getCurrentUser()
        assertTrue(userResult.isSuccess)
        val user = userResult.getOrThrow()
        assertNotNull(user)
        assertEquals("chef@bytebites.pl", user.email)
    }

    @Test
    fun `reset current user to null on logout`() = runTest {
        val logoutResult = repository.logout()
        assertTrue(logoutResult.isSuccess)

        val userResult = repository.getCurrentUser()
        assertTrue(userResult.isSuccess)
        assertNull(userResult.getOrThrow())
    }

}

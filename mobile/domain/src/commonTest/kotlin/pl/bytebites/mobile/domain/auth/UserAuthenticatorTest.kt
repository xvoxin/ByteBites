package pl.bytebites.mobile.domain.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.model.User
import pl.bytebites.mobile.domain.repository.AuthRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private class FakeAuthRepository : AuthRepository {
    private val currentUserFlow = MutableStateFlow<User?>(null)
    var loginError: Throwable? = null
    var logoutError: Throwable? = null
    var loggedInUser: Pair<String, String>? = null
    var logoutCalled = false

    override fun getCurrentUser(): Flow<User?> = currentUserFlow

    override suspend fun login(email: String, password: String) {
        val error = loginError
        if (error != null) throw error
        loggedInUser = email to password
    }

    override suspend fun logout() {
        logoutCalled = true
        val error = logoutError
        if (error != null) throw error
    }
}

class UserAuthenticatorTest {

    private val authRepository = FakeAuthRepository()
    private val authenticator = UserAuthenticator(authRepository)

    @Test
    fun `when logout succeeds, return Result success`() = runTest {
        val result = authenticator.logout()

        assertTrue(result.isSuccess)
        assertTrue(authRepository.logoutCalled)
    }

    @Test
    fun `when logout repository throws exception, return Result failure`() = runTest {
        authRepository.logoutError = IllegalStateException("Network unreachable")

        val result = authenticator.logout()

        assertTrue(result.isFailure)
        assertEquals("Network unreachable", result.exceptionOrNull()?.message)
    }

    @Test
    fun `when login credentials are blank, return Result failure without calling repository`() = runTest {
        val emptyEmailResult = authenticator.login("", "secret")
        assertTrue(emptyEmailResult.isFailure)

        val emptyPasswordResult = authenticator.login("test@bytebites.pl", "")
        assertTrue(emptyPasswordResult.isFailure)
        assertEquals(null, authRepository.loggedInUser)
    }

    @Test
    fun `when login succeeds, return Result success`() = runTest {
        val result = authenticator.login("chef@bytebites.pl", "secret123")

        assertTrue(result.isSuccess)
        assertEquals("chef@bytebites.pl" to "secret123", authRepository.loggedInUser)
    }

    @Test
    fun `when login repository throws exception, return Result failure`() = runTest {
        authRepository.loginError = IllegalArgumentException("Invalid credentials")

        val result = authenticator.login("chef@bytebites.pl", "wrong")

        assertTrue(result.isFailure)
        assertEquals("Invalid credentials", result.exceptionOrNull()?.message)
    }
}

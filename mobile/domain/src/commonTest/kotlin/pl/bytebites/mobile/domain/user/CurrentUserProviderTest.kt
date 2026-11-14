package pl.bytebites.mobile.domain.user

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.FakeAuthRepository
import pl.bytebites.mobile.domain.model.User
import pl.bytebites.mobile.domain.state.QueryState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CurrentUserProviderTest {

    private val fakeAuthRepository = FakeAuthRepository()
    private val currentUserProvider = RepositoryCurrentUserProvider(fakeAuthRepository)

    @Test
    fun `emit active user when repository succeeds`() = runTest {
        fakeAuthRepository.currentUserResult = Result.success(sampleUser())

        currentUserProvider.provide().test {
            val item = awaitItem()
            assertIs<QueryState.Value<User?>>(item)
            assertEquals(sampleUser(), item.value)
            awaitComplete()
        }
    }

    @Test
    fun `emit null when guest session active`() = runTest {
        fakeAuthRepository.currentUserResult = Result.success(null)

        currentUserProvider.provide().test {
            val item = awaitItem()
            assertIs<QueryState.Value<User?>>(item)
            assertEquals(null, item.value)
            awaitComplete()
        }
    }

    @Test
    fun `emit error state on repository failure`() = runTest {
        fakeAuthRepository.currentUserResult = Result.failure(sampleError)

        currentUserProvider.provide().test {
            val item = awaitItem()
            assertIs<QueryState.Error>(item)
            assertEquals(sampleError, item.throwable)
            awaitComplete()
        }
    }

    private val sampleError = RuntimeException("Auth service unavailable")

    private fun sampleUser() = User(
        id = "u1",
        displayName = "Ania",
        username = "@ania_cooks",
        email = "ania@bytebites.pl"
    )

}

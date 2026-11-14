package pl.bytebites.mobile.presentation.screen.profile.interactor

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.model.User as DomainUser
import pl.bytebites.mobile.domain.state.QueryState as DomainQueryState
import pl.bytebites.mobile.domain.user.CurrentUserProvider
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileUser
import pl.bytebites.mobile.presentation.state.QueryState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class FakeDomainCurrentUserProvider : CurrentUserProvider {

    var result: DomainQueryState<DomainUser?> = DomainQueryState.Value(null)

    override fun provide(): Flow<DomainQueryState<DomainUser?>> = flowOf(result)

}

class ProfileUserProviderInteractorTest {

    private val fakeUserProvider = FakeDomainCurrentUserProvider()
    private val interactor = ProfileUserProviderInteractor(fakeUserProvider)

    @Test
    fun `map domain user to presentation profile user`() = runTest {
        fakeUserProvider.result = DomainQueryState.Value(sampleDomainUser)

        interactor.provideCurrentUser().test {
            val item = awaitItem()
            assertIs<QueryState.Value<ProfileUser?>>(item)
            val user = item.value
            assertEquals("u1", user?.id)
            assertEquals("Ania", user?.displayName)
            assertEquals("@ania_cooks", user?.username)
            awaitComplete()
        }
    }

    @Test
    fun `emit null when domain user null`() = runTest {
        fakeUserProvider.result = DomainQueryState.Value(null)

        interactor.provideCurrentUser().test {
            val item = awaitItem()
            assertIs<QueryState.Value<ProfileUser?>>(item)
            assertNull(item.value)
            awaitComplete()
        }
    }

    @Test
    fun `propagate error state from domain user provider`() = runTest {
        fakeUserProvider.result = DomainQueryState.Error(sampleUserError)

        interactor.provideCurrentUser().test {
            val item = awaitItem()
            assertIs<QueryState.Error>(item)
            assertEquals(sampleUserError, item.throwable)
            awaitComplete()
        }
    }

    private val sampleUserError = RuntimeException("Domain user error")

    private val sampleDomainUser = DomainUser(
        id = "u1",
        displayName = "Ania",
        username = "ania_cooks",
        email = "ania@bytebites.pl"
    )

}

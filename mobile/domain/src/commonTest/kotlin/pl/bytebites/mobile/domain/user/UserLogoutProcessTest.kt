package pl.bytebites.mobile.domain.user

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.FakeAuthRepository
import pl.bytebites.mobile.domain.state.CommandState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class UserLogoutProcessTest {

    private val fakeAuthRepository = FakeAuthRepository()
    private val userLogoutProcess = SessionTerminationProcess(fakeAuthRepository)

    @Test
    fun `when repository logout succeeds, emit CommandState Success`() = runTest {
        fakeAuthRepository.logoutResult = Result.success(Unit)

        userLogoutProcess.logout().test {
            val item = awaitItem()
            assertIs<CommandState.Success>(item)
            assertEquals(1, fakeAuthRepository.logoutCallCount)
            awaitComplete()
        }
    }

    @Test
    fun `when repository logout fails with exception, emit CommandState Error with throwable`() = runTest {
        fakeAuthRepository.logoutResult = Result.failure(sampleError)

        userLogoutProcess.logout().test {
            val item = awaitItem()
            assertIs<CommandState.Error>(item)
            assertEquals(sampleError, item.throwable)
            awaitComplete()
        }
    }

    private val sampleError = RuntimeException("Logout network error")

}

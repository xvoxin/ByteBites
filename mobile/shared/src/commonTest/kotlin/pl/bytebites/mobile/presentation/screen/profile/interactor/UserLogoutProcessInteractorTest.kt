package pl.bytebites.mobile.presentation.screen.profile.interactor

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.state.CommandState as DomainCommandState
import pl.bytebites.mobile.domain.user.UserLogoutProcess as DomainUserLogoutProcess
import pl.bytebites.mobile.presentation.state.CommandState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class FakeDomainUserLogoutProcess : DomainUserLogoutProcess {

    var result: DomainCommandState = DomainCommandState.Success

    override fun logout(): Flow<DomainCommandState> = flowOf(result)

}

class UserLogoutProcessInteractorTest {

    private val fakeLogoutProcess = FakeDomainUserLogoutProcess()
    private val interactor = UserLogoutProcessInteractor(fakeLogoutProcess)

    @Test
    fun `propagate success from domain logout process`() = runTest {
        fakeLogoutProcess.result = DomainCommandState.Success

        interactor.logout().test {
            val item = awaitItem()
            assertIs<CommandState.Success>(item)
            awaitComplete()
        }
    }

    @Test
    fun `propagate error state from domain logout process`() = runTest {
        fakeLogoutProcess.result = DomainCommandState.Error(sampleLogoutError)

        interactor.logout().test {
            val item = awaitItem()
            assertIs<CommandState.Error>(item)
            assertEquals(sampleLogoutError, item.throwable)
            awaitComplete()
        }
    }

    private val sampleLogoutError = RuntimeException("Domain logout error")

}

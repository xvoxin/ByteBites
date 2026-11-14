package pl.bytebites.mobile.presentation.screen.profile

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pl.bytebites.mobile.presentation.resource.StringValue
import pl.bytebites.mobile.presentation.screen.profile.interactor.CurrentUserProvidingState
import pl.bytebites.mobile.presentation.screen.profile.interactor.LogoutProcessingState
import pl.bytebites.mobile.presentation.screen.profile.interactor.ProfileUserProvider
import pl.bytebites.mobile.presentation.screen.profile.interactor.UserLogoutProcess
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileEffect
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileIntent
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileUser
import pl.bytebites.mobile.presentation.screen.profile.viewmodel.ProfileScreenViewModel
import pl.bytebites.mobile.presentation.state.CommandState
import pl.bytebites.mobile.presentation.state.QueryState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class FakeProfileUserProvider : ProfileUserProvider {

    val userFlow = MutableStateFlow<CurrentUserProvidingState>(QueryState.Value(null))

    override fun provideCurrentUser(): Flow<CurrentUserProvidingState> = userFlow

}

class FakeUserLogoutProcess : UserLogoutProcess {

    var logoutState: LogoutProcessingState = CommandState.Success
    var logoutCallCount = 0

    override fun logout(): Flow<LogoutProcessingState> = flow {
        logoutCallCount++
        emit(logoutState)
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var userProvider: FakeProfileUserProvider
    private lateinit var logoutProcess: FakeUserLogoutProcess
    private lateinit var viewModel: ProfileScreenViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userProvider = FakeProfileUserProvider()
        logoutProcess = FakeUserLogoutProcess()
        viewModel = ProfileScreenViewModel(userProvider, logoutProcess)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `emit guest state when user is not logged in`() = runTest {
        viewModel.state.test {
            testDispatcher.scheduler.advanceUntilIdle()

            val state = expectMostRecentItem()
            assertFalse(state.isLoading)
            assertNull(state.user)
        }
    }

    @Test
    fun `emit user nickname when user is logged in`() = runTest {
        userProvider.userFlow.value = QueryState.Value(sampleUser)

        viewModel.state.test {
            testDispatcher.scheduler.advanceUntilIdle()

            val state = expectMostRecentItem()
            assertFalse(state.isLoading)
            assertEquals("Ania", state.user?.displayName)
            assertEquals("@ania_cooks", state.user?.username)
        }
    }

    @Test
    fun `emit navigate to auth effect when login button clicked`() = runTest {
        viewModel.effect.test {
            viewModel.onIntent(ProfileIntent.LoginClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertEquals(ProfileEffect.NavigateToAuth, effect)
        }
    }

    @Test
    fun `logout user when logout button clicked`() = runTest {
        userProvider.userFlow.value = QueryState.Value(sampleUser)

        viewModel.onIntent(ProfileIntent.LogoutClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, logoutProcess.logoutCallCount)
    }

    @Test
    fun `update error dialog and set loading false on observe user failure`() = runTest {
        userProvider.userFlow.value = QueryState.Error(sampleUserError)

        viewModel.errorDialog.test {
            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = expectMostRecentItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Const("Failed to fetch user profile"), errorState.message)
            assertFalse(viewModel.state.value.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `update error dialog on logout failure`() = runTest {
        logoutProcess.logoutState = CommandState.Error(sampleLogoutError)

        viewModel.errorDialog.test {
            viewModel.onIntent(ProfileIntent.LogoutClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = expectMostRecentItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Const("Logout failed due to network error"), errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `reset error dialog when error dismissed`() = runTest {
        userProvider.userFlow.value = QueryState.Error(sampleUserError)
        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(viewModel.errorDialog.value)
        viewModel.dismissError()

        assertNull(viewModel.errorDialog.value)
    }

    @Test
    fun `ignore duplicate logout clicks when logout is already running`() = runTest {
        viewModel.onIntent(ProfileIntent.LogoutClicked)
        viewModel.onIntent(ProfileIntent.LogoutClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, logoutProcess.logoutCallCount)
    }

    private val sampleUserError = RuntimeException("Failed to fetch user profile")

    private val sampleLogoutError = RuntimeException("Logout failed due to network error")

    private val sampleUser = ProfileUser(
        id = "u1",
        displayName = "Ania",
        username = "@ania_cooks",
        email = "ania@bytebites.pl"
    )

}

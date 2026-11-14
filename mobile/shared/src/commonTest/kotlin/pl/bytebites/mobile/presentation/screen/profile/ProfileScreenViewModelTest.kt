package pl.bytebites.mobile.presentation.screen.profile

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pl.bytebites.mobile.presentation.resource.StringValue
import pl.bytebites.mobile.presentation.screen.profile.interactor.ProfileProvider
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileEffect
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileIntent
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileUser
import pl.bytebites.mobile.presentation.screen.profile.viewmodel.ProfileScreenViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class FakeProfileProvider(
    initialUser: ProfileUser? = null
) : ProfileProvider {

    val userFlow = MutableStateFlow(initialUser)
    var currentUserError: Throwable? = null
    var logoutResult: Result<Unit> = Result.success(Unit)
    var logoutCallCount = 0

    override fun getCurrentUser(): Flow<ProfileUser?> = flow {
        val error = currentUserError
        if (error != null) {
            throw error
        } else {
            emitAll(userFlow)
        }
    }

    override suspend fun logout(): Result<Unit> {
        logoutCallCount++
        val result = logoutResult
        if (result.isSuccess) {
            userFlow.value = null
        }
        return result
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeProvider: FakeProfileProvider
    private lateinit var viewModel: ProfileScreenViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeProvider = FakeProfileProvider()
        viewModel = ProfileScreenViewModel(fakeProvider)
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
        val sampleUser = ProfileUser(
            id = "u1",
            displayName = "Ania",
            username = "@ania_cooks",
            email = "ania@bytebites.pl"
        )
        val loggedInProvider = FakeProfileProvider(sampleUser)
        val loggedInViewModel = ProfileScreenViewModel(loggedInProvider)

        loggedInViewModel.state.test {
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
        val sampleUser = ProfileUser(
            id = "u1",
            displayName = "Ania",
            username = "@ania_cooks",
            email = "ania@bytebites.pl"
        )
        fakeProvider.userFlow.value = sampleUser

        viewModel.state.test {
            viewModel.onIntent(ProfileIntent.LogoutClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            val state = expectMostRecentItem()
            assertNull(state.user)
        }
    }

    @Test
    fun `when observe user fails, update errorDialog state and set isLoading to false`() = runTest {
        val failingProvider = FakeProfileProvider().apply {
            currentUserError = RuntimeException("Failed to fetch user profile")
        }
        val failingViewModel = ProfileScreenViewModel(failingProvider)

        failingViewModel.errorDialog.test {
            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = expectMostRecentItem()
            assertNotNull(errorState, "errorDialog state should not be null on failure")
            assertEquals(StringValue.Const("Failed to fetch user profile"), errorState.message)
            assertFalse(failingViewModel.state.value.isLoading, "isLoading should be false after error")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when logout fails, update errorDialog state`() = runTest {
        val sampleUser = ProfileUser(
            id = "u1",
            displayName = "Ania",
            username = "@ania_cooks",
            email = "ania@bytebites.pl"
        )
        fakeProvider.userFlow.value = sampleUser
        fakeProvider.logoutResult = Result.failure(RuntimeException("Logout failed due to network error"))

        viewModel.errorDialog.test {
            viewModel.onIntent(ProfileIntent.LogoutClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = expectMostRecentItem()
            assertNotNull(errorState, "errorDialog should be shown when logout fails")
            assertEquals(StringValue.Const("Logout failed due to network error"), errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when error is dismissed, errorDialog state resets to null`() = runTest {
        val failingProvider = FakeProfileProvider().apply {
            currentUserError = RuntimeException("Failed to fetch user profile")
        }
        val failingViewModel = ProfileScreenViewModel(failingProvider)
        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(failingViewModel.errorDialog.value, "errorDialog should be present before dismissal")
        failingViewModel.dismissError()

        assertNull(failingViewModel.errorDialog.value, "errorDialog should be null after dismissal")
    }

    @Test
    fun `when logout is already running, duplicate logout clicks are ignored`() = runTest {
        val sampleUser = ProfileUser(
            id = "u1",
            displayName = "Ania",
            username = "@ania_cooks",
            email = "ania@bytebites.pl"
        )
        fakeProvider.userFlow.value = sampleUser

        viewModel.onIntent(ProfileIntent.LogoutClicked)
        viewModel.onIntent(ProfileIntent.LogoutClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, fakeProvider.logoutCallCount)
    }

}

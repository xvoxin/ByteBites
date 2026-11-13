package pl.bytebites.mobile.presentation.screen.profile

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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
import kotlin.test.assertNull

class FakeProfileProvider(
    initialUser: ProfileUser? = null
) : ProfileProvider {

    val userFlow = MutableStateFlow(initialUser)

    override fun getCurrentUser(): Flow<ProfileUser?> = userFlow

    override suspend fun logout(): Result<Unit> {
        userFlow.value = null
        return Result.success(Unit)
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

}

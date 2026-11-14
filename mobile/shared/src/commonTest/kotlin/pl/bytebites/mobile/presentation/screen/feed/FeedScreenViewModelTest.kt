package pl.bytebites.mobile.presentation.screen.feed

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pl.bytebites.mobile.presentation.resource.StringValue
import pl.bytebites.mobile.presentation.screen.feed.interactor.FeedProvider
import pl.bytebites.mobile.presentation.screen.feed.interactor.FeedProvidingState
import pl.bytebites.mobile.presentation.screen.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.screen.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.screen.feed.model.Recipe
import pl.bytebites.mobile.presentation.screen.feed.viewmodel.FeedScreenViewModel
import pl.bytebites.mobile.presentation.state.QueryState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class FakeFeedProvider : FeedProvider {

    val recipesFlow = MutableStateFlow<FeedProvidingState>(QueryState.Value(emptyList()))

    override fun provideRecipes(): Flow<FeedProvidingState> = recipesFlow

}

@OptIn(ExperimentalCoroutinesApi::class)
class FeedScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var feedProvider: FakeFeedProvider
    private lateinit var viewModel: FeedScreenViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        feedProvider = FakeFeedProvider()
        feedProvider.recipesFlow.value = QueryState.Value(sampleRecipes)
        viewModel = FeedScreenViewModel(feedProvider)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load recipes and update state`() = runTest {
        viewModel.state.test {
            testDispatcher.scheduler.advanceUntilIdle()

            val state = expectMostRecentItem()
            assertFalse(state.isLoading)
            assertEquals(1, state.recipes.size)
            assertEquals("Test Recipe", state.recipes.first().title)
        }
    }

    @Test
    fun `emit navigate to detail effect on recipe click`() = runTest {
        viewModel.effect.test {
            viewModel.onIntent(FeedIntent.RecipeClicked("1"))
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertEquals(FeedEffect.NavigateToDetail("1"), effect)
        }
    }

    @Test
    fun `emit navigate to profile effect on profile click`() = runTest {
        viewModel.effect.test {
            viewModel.onIntent(FeedIntent.ProfileClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertEquals(FeedEffect.NavigateToProfile, effect)
        }
    }

    @Test
    fun `update error dialog and set loading false on recipes loading failure`() = runTest {
        feedProvider.recipesFlow.value = QueryState.Error(sampleError)

        viewModel.errorDialog.test {
            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = expectMostRecentItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Const("Failed to fetch recipes"), errorState.message)
            assertFalse(viewModel.state.value.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `reset error dialog when error dismissed`() = runTest {
        feedProvider.recipesFlow.value = QueryState.Error(sampleError)
        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(viewModel.errorDialog.value)
        viewModel.dismissError()

        assertNull(viewModel.errorDialog.value)
    }

    private val sampleError = RuntimeException("Failed to fetch recipes")

    private val sampleRecipes = listOf(
        Recipe(
            id = "1",
            title = "Test Recipe",
            categoryName = "*Kategoria*",
            prepTime = "10 min",
            difficulty = "Łatwe",
            rating = "5.0"
        )
    )

}

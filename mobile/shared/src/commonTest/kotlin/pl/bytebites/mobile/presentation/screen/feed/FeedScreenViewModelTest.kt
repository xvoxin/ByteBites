package pl.bytebites.mobile.presentation.screen.feed

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.provider.RecipeProvider
import pl.bytebites.mobile.presentation.screen.feed.interactor.FeedProviderInteractor
import pl.bytebites.mobile.presentation.screen.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.screen.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.screen.feed.viewmodel.FeedScreenViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import pl.bytebites.mobile.presentation.resource.StringValue

@OptIn(ExperimentalCoroutinesApi::class)
class FeedScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val sampleRecipes = listOf(
        Recipe(
            id = "1",
            title = "Test Recipe",
            categoryId = "mains",
            categoryName = "Obiady",
            prepTime = "10 min",
            difficulty = "Łatwe",
            rating = 5.0,
            servings = 2,
            description = "Test",
            ingredients = listOf("a"),
            steps = listOf("b")
        )
    )

    private lateinit var repository: FakeRecipeRepository
    private lateinit var recipeProvider: RecipeProvider
    private lateinit var interactor: FeedProviderInteractor
    private lateinit var viewModel: FeedScreenViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeRecipeRepository(sampleRecipes)
        recipeProvider = RecipeProvider(repository)
        interactor = FeedProviderInteractor(recipeProvider)
        viewModel = FeedScreenViewModel(interactor)
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
    fun `emit navigate to profile effect on profile icon click`() = runTest {
        viewModel.effect.test {
            viewModel.onIntent(FeedIntent.ProfileClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertEquals(FeedEffect.NavigateToProfile, effect)
        }
    }

    @Test
    fun `when recipes loading fails, update errorDialog state and set isLoading to false`() = runTest {
        repository.errorToThrow = RuntimeException("Failed to fetch recipes")
        val failingViewModel = FeedScreenViewModel(interactor)

        failingViewModel.errorDialog.test {
            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = expectMostRecentItem()
            assertNotNull(errorState, "errorDialog state should not be null on failure")
            assertEquals(StringValue.Const("Failed to fetch recipes"), errorState.message)
            assertFalse(failingViewModel.state.value.isLoading, "isLoading should be false after error")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when error is dismissed, errorDialog state resets to null`() = runTest {
        repository.errorToThrow = RuntimeException("Failed to fetch recipes")
        val failingViewModel = FeedScreenViewModel(interactor)
        testDispatcher.scheduler.advanceUntilIdle()

        failingViewModel.dismissError()

        assertNull(failingViewModel.errorDialog.value)
    }

}

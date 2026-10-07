package pl.bytebites.mobile.presentation.feed

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.provider.RecipeProvider
import pl.bytebites.mobile.presentation.feed.interactor.DefaultFeedInteractor
import pl.bytebites.mobile.presentation.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.feed.viewmodel.DefaultFeedViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultFeedViewModelTest {

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
            steps = listOf("b"),
            emoji = "🍲"
        )
    )

    private lateinit var repository: FakeRecipeRepository
    private lateinit var recipeProvider: RecipeProvider
    private lateinit var interactor: DefaultFeedInteractor
    private lateinit var viewModel: DefaultFeedViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeRecipeRepository(sampleRecipes)
        recipeProvider = RecipeProvider(repository)
        interactor = DefaultFeedInteractor(recipeProvider)
        viewModel = DefaultFeedViewModel(interactor)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun viewModel_loads_recipes_and_updates_state() = runTest {
        viewModel.state.test {
            testDispatcher.scheduler.advanceUntilIdle()

            val state = expectMostRecentItem()
            assertFalse(state.isLoading)
            assertEquals(1, state.recipes.size)
            assertEquals("Test Recipe", state.recipes.first().title)
        }
    }

    @Test
    fun recipeClicked_intent_emits_NavigateToDetail_effect() = runTest {
        viewModel.effect.test {
            viewModel.onIntent(FeedIntent.RecipeClicked("1"))
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertEquals(FeedEffect.NavigateToDetail("1"), effect)
        }
    }

}

package pl.bytebites.mobile.presentation.screen.recipe_detail

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.provider.RecipeDetailsProvider
import pl.bytebites.mobile.presentation.screen.feed.FakeRecipeRepository
import pl.bytebites.mobile.presentation.screen.recipe_detail.interactor.RecipeDetailProviderInteractor
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailEffect
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailIntent
import pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel.RecipeDetailScreenViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import pl.bytebites.mobile.presentation.resource.StringValue

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeDetailScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val sampleRecipe = Recipe(
        id = "recipe_100",
        title = "Kremowa Zupa Dyniowa",
        categoryId = "mains",
        categoryName = "Obiady",
        prepTime = "30 min",
        difficulty = "Łatwe",
        rating = 4.9,
        servings = 4,
        description = "Opis zupy",
        ingredients = listOf("Dynia"),
        steps = listOf("Ugotuj")
    )

    private lateinit var repository: FakeRecipeRepository
    private lateinit var domainDetailsProvider: RecipeDetailsProvider
    private lateinit var interactor: RecipeDetailProviderInteractor
    private lateinit var viewModel: RecipeDetailScreenViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeRecipeRepository(listOf(sampleRecipe))
        domainDetailsProvider = RecipeDetailsProvider(repository)
        interactor = RecipeDetailProviderInteractor(domainDetailsProvider)
        viewModel = RecipeDetailScreenViewModel(interactor)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load recipe detail and update state`() = runTest {
        viewModel.state.test {
            viewModel.onIntent(RecipeDetailIntent.LoadRecipeDetail("recipe_100"))
            testDispatcher.scheduler.advanceUntilIdle()

            val state = expectMostRecentItem()
            assertFalse(state.isLoading)
            assertNotNull(state.recipeDetail)
            assertEquals("Kremowa Zupa Dyniowa", state.recipeDetail.title)
        }
    }

    @Test
    fun `emit navigate back effect on back click`() = runTest {
        viewModel.effect.test {
            viewModel.onIntent(RecipeDetailIntent.BackClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertEquals(RecipeDetailEffect.NavigateBack, effect)
        }
    }

    @Test
    fun `when recipe detail loading fails, update errorDialog state and set isLoading to false`() = runTest {
        repository.errorToThrow = RuntimeException("Failed to load recipe details")

        viewModel.errorDialog.test {
            viewModel.onIntent(RecipeDetailIntent.LoadRecipeDetail("recipe_100"))
            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = expectMostRecentItem()
            assertNotNull(errorState, "errorDialog state should not be null on failure")
            assertEquals(StringValue.Const("Failed to load recipe details"), errorState.message)
            assertFalse(viewModel.state.value.isLoading, "isLoading should be false after error")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when error is dismissed, errorDialog state resets to null`() = runTest {
        repository.errorToThrow = RuntimeException("Failed to load recipe details")
        viewModel.onIntent(RecipeDetailIntent.LoadRecipeDetail("recipe_100"))
        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(viewModel.errorDialog.value, "errorDialog should be present before dismissal")
        viewModel.dismissError()

        assertNull(viewModel.errorDialog.value, "errorDialog should be null after dismissal")
    }

}

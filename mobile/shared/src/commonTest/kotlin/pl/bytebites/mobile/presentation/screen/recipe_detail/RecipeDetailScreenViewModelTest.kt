package pl.bytebites.mobile.presentation.screen.recipe_detail

import app.cash.turbine.test
import bytebites.shared.generated.resources.Res
import bytebites.shared.generated.resources.recipe_detail_not_found
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pl.bytebites.mobile.presentation.resource.StringValue
import pl.bytebites.mobile.presentation.screen.recipe_detail.interactor.RecipeDetailProvider
import pl.bytebites.mobile.presentation.screen.recipe_detail.interactor.RecipeDetailProvidingState
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetail
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailEffect
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailIntent
import pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel.RecipeDetailScreenViewModel
import pl.bytebites.mobile.presentation.state.QueryState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class FakeRecipeDetailProvider : RecipeDetailProvider {

    val recipeDetailFlow = MutableStateFlow<RecipeDetailProvidingState>(QueryState.Value(null))

    override fun provideRecipeDetail(id: String): Flow<RecipeDetailProvidingState> = recipeDetailFlow

}

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeDetailScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var recipeDetailProvider: FakeRecipeDetailProvider
    private lateinit var viewModel: RecipeDetailScreenViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        recipeDetailProvider = FakeRecipeDetailProvider()
        recipeDetailProvider.recipeDetailFlow.value = QueryState.Value(sampleRecipe)
        viewModel = RecipeDetailScreenViewModel(recipeDetailProvider)
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
    fun `update error dialog and set loading false on recipe detail loading failure`() = runTest {
        recipeDetailProvider.recipeDetailFlow.value = QueryState.Error(sampleError)

        viewModel.errorDialog.test {
            viewModel.onIntent(RecipeDetailIntent.LoadRecipeDetail("recipe_100"))
            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = expectMostRecentItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Const("Failed to load recipe details"), errorState.message)
            assertFalse(viewModel.state.value.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `reset error dialog when error dismissed`() = runTest {
        recipeDetailProvider.recipeDetailFlow.value = QueryState.Error(sampleError)
        viewModel.onIntent(RecipeDetailIntent.LoadRecipeDetail("recipe_100"))
        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(viewModel.errorDialog.value)
        viewModel.dismissError()

        assertNull(viewModel.errorDialog.value)
    }

    @Test
    fun `update state with not found error when recipe detail is null`() = runTest {
        recipeDetailProvider.recipeDetailFlow.value = QueryState.Value(null)

        viewModel.state.test {
            viewModel.onIntent(RecipeDetailIntent.LoadRecipeDetail("non_existing_id"))
            testDispatcher.scheduler.advanceUntilIdle()

            val state = expectMostRecentItem()
            assertFalse(state.isLoading)
            assertNull(state.recipeDetail)
            assertEquals(StringValue.Resource(Res.string.recipe_detail_not_found), state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private val sampleError = RuntimeException("Failed to load recipe details")

    private val sampleRecipe = RecipeDetail(
        id = "recipe_100",
        title = "Kremowa Zupa Dyniowa",
        categoryName = "*Kategoria*",
        prepTime = "30 min",
        difficulty = "Łatwe",
        rating = "4.9",
        servings = "4",
        description = "Opis zupy",
        ingredients = listOf("Dynia"),
        steps = listOf("Ugotuj"),
        authorName = "ByteBites Chef"
    )

}

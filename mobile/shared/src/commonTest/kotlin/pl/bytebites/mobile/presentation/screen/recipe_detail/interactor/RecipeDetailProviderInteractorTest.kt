package pl.bytebites.mobile.presentation.screen.recipe_detail.interactor

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.model.Recipe as DomainRecipe
import pl.bytebites.mobile.domain.recipe.RecipeDetailsProvider
import pl.bytebites.mobile.domain.state.QueryState as DomainQueryState
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetail
import pl.bytebites.mobile.presentation.state.QueryState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class FakeDomainRecipeDetailsProvider : RecipeDetailsProvider {

    var result: DomainQueryState<DomainRecipe?> = DomainQueryState.Value(null)

    override fun provide(id: String): Flow<DomainQueryState<DomainRecipe?>> = flowOf(result)

}

class RecipeDetailProviderInteractorTest {

    private val fakeDetailsProvider = FakeDomainRecipeDetailsProvider()
    private val interactor = RecipeDetailProviderInteractor(fakeDetailsProvider)

    @Test
    fun `map domain recipe details to presentation detail`() = runTest {
        fakeDetailsProvider.result = DomainQueryState.Value(sampleDomainRecipe)

        interactor.provideRecipeDetail("1").test {
            val item = awaitItem()
            assertIs<QueryState.Value<RecipeDetail?>>(item)
            val detail = item.value
            assertEquals("1", detail?.id)
            assertEquals("Pancakes", detail?.title)
            assertEquals("*Kategoria*", detail?.categoryName)
            assertEquals("2", detail?.servings)
            assertEquals("ByteBites Chef", detail?.authorName)
            awaitComplete()
        }
    }

    @Test
    fun `emit null when domain recipe details null`() = runTest {
        fakeDetailsProvider.result = DomainQueryState.Value(null)

        interactor.provideRecipeDetail("1").test {
            val item = awaitItem()
            assertIs<QueryState.Value<RecipeDetail?>>(item)
            assertNull(item.value)
            awaitComplete()
        }
    }

    @Test
    fun `propagate error state from domain details provider`() = runTest {
        fakeDetailsProvider.result = DomainQueryState.Error(sampleError)

        interactor.provideRecipeDetail("1").test {
            val item = awaitItem()
            assertIs<QueryState.Error>(item)
            assertEquals(sampleError, item.throwable)
            awaitComplete()
        }
    }

    private val sampleError = RuntimeException("Domain details error")

    private val sampleDomainRecipe = DomainRecipe(
        id = "1",
        title = "Pancakes",
        prepTime = "15 min",
        difficulty = "Easy",
        rating = 4.7,
        servings = 2,
        description = "Fluffy pancakes",
        ingredients = listOf("Flour", "Milk"),
        steps = listOf("Mix", "Fry")
    )

}

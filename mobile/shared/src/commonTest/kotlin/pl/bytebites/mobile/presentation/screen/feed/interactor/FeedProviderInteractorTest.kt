package pl.bytebites.mobile.presentation.screen.feed.interactor

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.domain.model.Recipe as DomainRecipe
import pl.bytebites.mobile.domain.recipe.RecipeFeedProvider
import pl.bytebites.mobile.domain.state.QueryState as DomainQueryState
import pl.bytebites.mobile.presentation.screen.feed.model.Recipe
import pl.bytebites.mobile.presentation.state.QueryState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class FakeDomainRecipeFeedProvider : RecipeFeedProvider {

    var result: DomainQueryState<List<DomainRecipe>> = DomainQueryState.Value(emptyList())

    override fun provide(): Flow<DomainQueryState<List<DomainRecipe>>> = flowOf(result)

}

class FeedProviderInteractorTest {

    private val fakeFeedProvider = FakeDomainRecipeFeedProvider()
    private val interactor = FeedProviderInteractor(fakeFeedProvider)

    @Test
    fun `map domain recipes to presentation recipes`() = runTest {
        fakeFeedProvider.result = DomainQueryState.Value(sampleDomainRecipes)

        interactor.provideRecipes().test {
            val item = awaitItem()
            assertIs<QueryState.Value<List<Recipe>>>(item)
            assertEquals(1, item.value.size)
            assertEquals("Pancakes", item.value.first().title)
            assertEquals("*Kategoria*", item.value.first().categoryName)
            awaitComplete()
        }
    }

    @Test
    fun `propagate error state from domain feed provider`() = runTest {
        fakeFeedProvider.result = DomainQueryState.Error(sampleError)

        interactor.provideRecipes().test {
            val item = awaitItem()
            assertIs<QueryState.Error>(item)
            assertEquals(sampleError, item.throwable)
            awaitComplete()
        }
    }

    private val sampleError = RuntimeException("Domain error")

    private val sampleDomainRecipes = listOf(
        DomainRecipe(
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
    )

}

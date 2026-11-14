package pl.bytebites.mobile.presentation.screen.recipe_detail.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.domain.recipe.RecipeDetailsProvider
import pl.bytebites.mobile.domain.state.QueryState as DomainQueryState
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.toDetailPresentation
import pl.bytebites.mobile.presentation.state.QueryState

class RecipeDetailProviderInteractor(
    private val recipeDetailsProvider: RecipeDetailsProvider
) : RecipeDetailProvider {

    override fun provideRecipeDetail(id: String): Flow<RecipeDetailProvidingState> =
        recipeDetailsProvider.provide(id).map { state ->
            when (state) {
                is DomainQueryState.Value -> QueryState.Value(state.value?.toDetailPresentation())
                is DomainQueryState.Error -> QueryState.Error(state.throwable)
            }
        }

}

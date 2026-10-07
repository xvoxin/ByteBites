package pl.bytebites.mobile.presentation.feed.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.presentation.feed.model.Recipe
import pl.bytebites.mobile.presentation.feed.model.toPresentation
import pl.bytebites.mobile.domain.provider.RecipeProvider as DomainRecipeProvider

class FeedProviderInteractor(
    private val recipeProvider: DomainRecipeProvider
) : FeedProvider {

    override fun getRecipes(): Flow<List<Recipe>> =
        recipeProvider.getRecipes().map { list ->
            list.map { it.toPresentation() }
        }

}

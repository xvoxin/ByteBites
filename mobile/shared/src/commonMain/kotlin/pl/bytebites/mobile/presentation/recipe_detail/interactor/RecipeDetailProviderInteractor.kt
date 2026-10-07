package pl.bytebites.mobile.presentation.recipe_detail.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.domain.provider.RecipeDetailsProvider as DomainRecipeDetailsProvider
import pl.bytebites.mobile.presentation.recipe_detail.model.RecipeDetail
import pl.bytebites.mobile.presentation.recipe_detail.model.toDetailPresentation

class RecipeDetailProviderInteractor(
    private val recipeDetailsProvider: DomainRecipeDetailsProvider
) : RecipeDetailProvider {

    override fun getRecipeById(id: String): Flow<RecipeDetail?> =
        recipeDetailsProvider.getRecipeById(id).map { domainRecipe ->
            domainRecipe?.toDetailPresentation()
        }

}

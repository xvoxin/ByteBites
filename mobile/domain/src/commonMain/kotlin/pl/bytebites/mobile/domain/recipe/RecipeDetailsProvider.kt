package pl.bytebites.mobile.domain.recipe

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.repository.RecipeRepository
import pl.bytebites.mobile.domain.state.QueryState
import pl.bytebites.mobile.domain.state.queryFlow

interface RecipeDetailsProvider {

    fun provide(id: String): Flow<QueryState<Recipe?>>

}

class RepositoryRecipeDetailsProvider(
    private val recipeRepository: RecipeRepository
) : RecipeDetailsProvider {

    override fun provide(id: String): Flow<QueryState<Recipe?>> =
        queryFlow {
            recipeRepository.getRecipeById(id)
        }

}

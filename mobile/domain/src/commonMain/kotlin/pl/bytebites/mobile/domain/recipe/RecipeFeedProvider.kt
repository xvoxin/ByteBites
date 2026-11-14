package pl.bytebites.mobile.domain.recipe

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.repository.RecipeRepository
import pl.bytebites.mobile.domain.state.QueryState
import pl.bytebites.mobile.domain.state.queryFlow

interface RecipeFeedProvider {

    fun provide(): Flow<QueryState<List<Recipe>>>

}

class RepositoryRecipeFeedProvider(
    private val recipeRepository: RecipeRepository
) : RecipeFeedProvider {

    override fun provide(): Flow<QueryState<List<Recipe>>> = 
        queryFlow {
            recipeRepository.getRecipes()
        }

}

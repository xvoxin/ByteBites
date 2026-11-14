package pl.bytebites.mobile.presentation.screen.recipe_detail.model

import pl.bytebites.mobile.presentation.resource.StringValue

data class RecipeDetailState(
    val recipeDetail: RecipeDetail? = null,
    val isLoading: Boolean = false,
    val error: StringValue? = null
)

sealed interface RecipeDetailIntent {

    data class LoadRecipeDetail(val recipeId: String) : RecipeDetailIntent

    data object BackClicked : RecipeDetailIntent

}

sealed interface RecipeDetailEffect {

    data object NavigateBack : RecipeDetailEffect

}

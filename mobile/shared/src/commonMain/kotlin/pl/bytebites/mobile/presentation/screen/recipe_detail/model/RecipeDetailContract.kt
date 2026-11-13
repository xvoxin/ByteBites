package pl.bytebites.mobile.presentation.screen.recipe_detail.model

data class RecipeDetailState(
    val recipeDetail: RecipeDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface RecipeDetailIntent {

    data class LoadRecipeDetail(val recipeId: String) : RecipeDetailIntent

    data object BackClicked : RecipeDetailIntent

}

sealed interface RecipeDetailEffect {

    data object NavigateBack : RecipeDetailEffect

}

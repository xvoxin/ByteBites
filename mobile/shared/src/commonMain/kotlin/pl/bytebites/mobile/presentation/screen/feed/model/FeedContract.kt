package pl.bytebites.mobile.presentation.screen.feed.model

data class FeedState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface FeedIntent {

    data object LoadRecipes : FeedIntent

    data class RecipeClicked(val recipeId: String) : FeedIntent

}

sealed interface FeedEffect {

    data class NavigateToDetail(val recipeId: String) : FeedEffect

}

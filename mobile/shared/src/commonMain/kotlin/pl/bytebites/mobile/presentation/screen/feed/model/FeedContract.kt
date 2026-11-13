package pl.bytebites.mobile.presentation.screen.feed.model

data class FeedState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface FeedIntent {

    data class RecipeClicked(val recipeId: String) : FeedIntent

    data object ProfileClicked : FeedIntent

}

sealed interface FeedEffect {

    data class NavigateToDetail(val recipeId: String) : FeedEffect

    data object NavigateToProfile : FeedEffect

}

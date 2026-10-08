package pl.bytebites.mobile.presentation.navigation

sealed interface NavKey {

    data object Feed : NavKey

    data class RecipeDetail(val recipeId: String) : NavKey

}

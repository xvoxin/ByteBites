package pl.bytebites.mobile.presentation.navigation

sealed interface NavKey {

    data object Feed : NavKey

    data class RecipeDetail(val recipeId: String) : NavKey

    data object Profile : NavKey

}

fun NavKey.toRoute(): String = when (this) {
    is NavKey.Feed -> "feed"
    is NavKey.Profile -> "profile"
    is NavKey.RecipeDetail -> "recipe_detail:$recipeId"
}

fun String.toNavKey(): NavKey = when {
    this == "feed" -> NavKey.Feed
    this == "profile" -> NavKey.Profile
    this.startsWith("recipe_detail:") -> NavKey.RecipeDetail(this.removePrefix("recipe_detail:"))
    else -> NavKey.Feed
}

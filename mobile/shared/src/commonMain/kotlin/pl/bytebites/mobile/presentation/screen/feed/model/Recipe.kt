package pl.bytebites.mobile.presentation.screen.feed.model

data class Recipe(
    val id: String,
    val title: String,
    val categoryName: String,
    val prepTime: String,
    val difficulty: String,
    val rating: String
)

package pl.bytebites.mobile.presentation.recipe_detail.model

data class RecipeDetail(
    val id: String,
    val title: String,
    val categoryName: String,
    val prepTime: String,
    val difficulty: String,
    val rating: String,
    val servings: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val emoji: String,
    val authorName: String
)

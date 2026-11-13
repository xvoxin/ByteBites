package pl.bytebites.mobile.domain.model

data class Recipe(
    val id: String,
    val title: String,
    val categoryId: String,
    val categoryName: String,
    val prepTime: String,
    val difficulty: String,
    val rating: Double,
    val servings: Int,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val authorId: String = "system",
    val authorName: String = "ByteBites Chef",
    val isFavorite: Boolean = false,
    val isFeatured: Boolean = false
)

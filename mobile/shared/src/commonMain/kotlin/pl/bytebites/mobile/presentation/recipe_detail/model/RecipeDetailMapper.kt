package pl.bytebites.mobile.presentation.recipe_detail.model

import pl.bytebites.mobile.domain.model.Recipe as DomainRecipe

fun DomainRecipe.toDetailPresentation(): RecipeDetail = RecipeDetail(
    id = id,
    title = title,
    categoryName = categoryName,
    prepTime = prepTime,
    difficulty = difficulty,
    rating = rating.toString(),
    servings = "$servings porcji",
    description = description,
    ingredients = ingredients,
    steps = steps,
    authorName = authorName
)

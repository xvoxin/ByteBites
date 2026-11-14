package pl.bytebites.mobile.presentation.screen.recipe_detail.model

import pl.bytebites.mobile.domain.model.Recipe as DomainRecipe

fun DomainRecipe.toDetailPresentation(): RecipeDetail =
    RecipeDetail(
        id = id,
        title = title,
        categoryName = "*Kategoria*",
        prepTime = prepTime,
        difficulty = difficulty,
        rating = rating.toString(),
        servings = servings.toString(),
        description = description,
        ingredients = ingredients,
        steps = steps,
        authorName = authorName
    )

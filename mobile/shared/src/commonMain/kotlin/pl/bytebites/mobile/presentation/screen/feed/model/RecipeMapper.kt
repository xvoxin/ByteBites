package pl.bytebites.mobile.presentation.screen.feed.model

import pl.bytebites.mobile.domain.model.Recipe as DomainRecipe

fun DomainRecipe.toPresentation(): Recipe = Recipe(
    id = id,
    title = title,
    categoryName = categoryName,
    prepTime = prepTime,
    difficulty = difficulty,
    rating = rating.toString()
)

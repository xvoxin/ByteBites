package pl.bytebites.mobile.presentation.feed.interactor

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.presentation.feed.model.Recipe

interface FeedProvider {

    fun getRecipes(): Flow<List<Recipe>>

}

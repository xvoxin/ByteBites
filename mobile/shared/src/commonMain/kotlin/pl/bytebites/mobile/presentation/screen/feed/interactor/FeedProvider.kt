package pl.bytebites.mobile.presentation.screen.feed.interactor

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.presentation.screen.feed.model.Recipe

interface FeedProvider {

    fun getRecipes(): Flow<List<Recipe>>

}

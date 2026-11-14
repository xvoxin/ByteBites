package pl.bytebites.mobile.presentation.screen.feed.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import pl.bytebites.mobile.presentation.error.ErrorHandler
import pl.bytebites.mobile.presentation.screen.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.screen.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.screen.feed.model.FeedState

abstract class FeedViewModel : ViewModel(), ErrorHandler {

    abstract val state: StateFlow<FeedState>
    abstract val effect: Flow<FeedEffect>

    abstract fun onIntent(intent: FeedIntent)

}

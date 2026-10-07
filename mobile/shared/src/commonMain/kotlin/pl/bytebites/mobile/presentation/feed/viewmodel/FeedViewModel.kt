package pl.bytebites.mobile.presentation.feed.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import pl.bytebites.mobile.presentation.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.feed.model.FeedState

abstract class FeedViewModel : ViewModel() {

    abstract val state: StateFlow<FeedState>
    abstract val effect: Flow<FeedEffect>

    abstract fun onIntent(intent: FeedIntent)

}

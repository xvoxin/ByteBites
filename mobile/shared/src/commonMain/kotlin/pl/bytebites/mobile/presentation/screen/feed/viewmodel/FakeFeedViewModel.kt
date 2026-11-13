package pl.bytebites.mobile.presentation.screen.feed.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import pl.bytebites.mobile.presentation.screen.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.screen.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.screen.feed.model.FeedState

class FakeFeedViewModel(
    initialState: FeedState = FeedState()
) : FeedViewModel() {

    override val state = MutableStateFlow(initialState)
    override val effect: Flow<FeedEffect> = Channel<FeedEffect>().receiveAsFlow()

    override fun onIntent(intent: FeedIntent) {}

}

package pl.bytebites.mobile.presentation.screen.profile.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileEffect
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileIntent
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileState

abstract class ProfileViewModel : ViewModel() {

    abstract val state: StateFlow<ProfileState>

    abstract val effect: Flow<ProfileEffect>

    abstract fun onIntent(intent: ProfileIntent)

}

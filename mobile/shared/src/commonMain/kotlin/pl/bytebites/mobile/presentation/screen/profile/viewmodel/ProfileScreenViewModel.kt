package pl.bytebites.mobile.presentation.screen.profile.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.bytebites.mobile.presentation.screen.profile.interactor.ProfileProvider
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileEffect
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileIntent
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileState

class ProfileScreenViewModel(
    private val profileProvider: ProfileProvider
) : ProfileViewModel() {

    override val state = MutableStateFlow(ProfileState(isLoading = true))

    private val effectChannel = Channel<ProfileEffect>(Channel.BUFFERED)
    override val effect: Flow<ProfileEffect> = effectChannel.receiveAsFlow()

    init {
        observeUser()
    }

    override fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoginClicked -> {
                viewModelScope.launch {
                    effectChannel.send(ProfileEffect.NavigateToAuth)
                }
            }

            is ProfileIntent.LogoutClicked -> {
                viewModelScope.launch {
                    profileProvider.logout()
                }
            }
        }
    }

    private fun observeUser() {
        viewModelScope.launch {
            profileProvider.getCurrentUser().collect { user ->
                state.update {
                    it.copy(
                        user = user,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

}

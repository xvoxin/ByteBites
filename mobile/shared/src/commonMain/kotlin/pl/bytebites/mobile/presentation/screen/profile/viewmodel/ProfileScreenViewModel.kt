package pl.bytebites.mobile.presentation.screen.profile.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.bytebites.mobile.presentation.error.DefaultErrorHandler
import pl.bytebites.mobile.presentation.error.ErrorHandler
import pl.bytebites.mobile.presentation.screen.profile.interactor.ProfileProvider
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileEffect
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileIntent
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileState

class ProfileScreenViewModel(
    private val profileProvider: ProfileProvider,
    errorHandler: ErrorHandler = DefaultErrorHandler()
) : ProfileViewModel(), ErrorHandler by errorHandler {

    override val state = MutableStateFlow(ProfileState(isLoading = true))

    private val effectChannel = Channel<ProfileEffect>(Channel.BUFFERED)
    override val effect: Flow<ProfileEffect> = effectChannel.receiveAsFlow()

    private var logoutJob: Job? = null

    init {
        observeUser()
    }

    override fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoginClicked -> navigateToAuth()
            is ProfileIntent.LogoutClicked -> logout()
        }
    }

    private fun navigateToAuth() {
        viewModelScope.launch {
            effectChannel.send(ProfileEffect.NavigateToAuth)
        }
    }

    private fun logout() {
        if (logoutJob?.isActive == true) return
        logoutJob = viewModelScope.launch {
            profileProvider.logout()
                .onFailure { throwable -> showError(throwable) }
        }
    }

    private fun observeUser() {
        viewModelScope.launch {
            profileProvider.getCurrentUser()
                .catch { throwable ->
                    showError(throwable)
                    state.update { it.copy(isLoading = false) }
                }
                .collect { user ->
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

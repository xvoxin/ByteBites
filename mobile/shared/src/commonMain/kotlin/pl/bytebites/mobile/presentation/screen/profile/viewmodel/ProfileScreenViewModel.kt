package pl.bytebites.mobile.presentation.screen.profile.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.bytebites.mobile.presentation.error.DefaultErrorHandler
import pl.bytebites.mobile.presentation.error.ErrorHandler
import pl.bytebites.mobile.presentation.screen.profile.interactor.ProfileUserProvider
import pl.bytebites.mobile.presentation.screen.profile.interactor.UserLogoutProcess
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileEffect
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileIntent
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileState
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileUser
import pl.bytebites.mobile.presentation.state.CommandState
import pl.bytebites.mobile.presentation.state.QueryState

class ProfileScreenViewModel(
    private val profileUserProvider: ProfileUserProvider,
    private val userLogoutProcess: UserLogoutProcess,
    errorHandler: ErrorHandler = DefaultErrorHandler()
) : ProfileViewModel(), ErrorHandler by errorHandler {

    override val state = MutableStateFlow(ProfileState(isLoading = true))

    private val effectChannel = Channel<ProfileEffect>(Channel.BUFFERED)

    override val effect: Flow<ProfileEffect> = effectChannel.receiveAsFlow()

    private var logoutJob: Job? = null

    init {
        provideUser()
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
            userLogoutProcess.logout().collect { commandState ->
                when (commandState) {
                    is CommandState.Success -> onLogoutSuccess()
                    is CommandState.Error -> onLogoutError(commandState.throwable)
                }
            }
        }
    }

    private fun provideUser() {
        viewModelScope.launch {
            profileUserProvider.provideCurrentUser().collect { queryState ->
                when (queryState) {
                    is QueryState.Value -> onUserProvided(queryState.value)
                    is QueryState.Error -> onProvidingError(queryState.throwable)
                }
            }
        }
    }

    private fun onUserProvided(user: ProfileUser?) {
        state.update {
            it.copy(
                user = user,
                isLoading = false,
                error = null
            )
        }
    }

    private fun onProvidingError(throwable: Throwable) {
        showError(throwable)
        state.update { it.copy(isLoading = false) }
    }

    private fun onLogoutSuccess() {
        // Handled reactively via provider stream
    }

    private fun onLogoutError(throwable: Throwable) {
        showError(throwable)
    }

}

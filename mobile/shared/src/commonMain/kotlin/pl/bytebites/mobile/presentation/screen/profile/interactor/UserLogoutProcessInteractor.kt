package pl.bytebites.mobile.presentation.screen.profile.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.domain.state.CommandState as DomainCommandState
import pl.bytebites.mobile.domain.user.UserLogoutProcess as DomainUserLogoutProcess
import pl.bytebites.mobile.presentation.state.CommandState

class UserLogoutProcessInteractor(
    private val userLogoutProcess: DomainUserLogoutProcess
) : UserLogoutProcess {

    override fun logout(): Flow<LogoutProcessingState> =
        userLogoutProcess.logout().map { state ->
            when (state) {
                is DomainCommandState.Success -> CommandState.Success
                is DomainCommandState.Error -> CommandState.Error(state.throwable)
            }
        }

}

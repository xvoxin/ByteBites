package pl.bytebites.mobile.presentation.screen.profile.interactor

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.presentation.state.CommandState

typealias LogoutProcessingState = CommandState

interface UserLogoutProcess {

    fun logout(): Flow<LogoutProcessingState>

}

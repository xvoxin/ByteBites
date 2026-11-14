package pl.bytebites.mobile.domain.user

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.domain.repository.AuthRepository
import pl.bytebites.mobile.domain.state.CommandState
import pl.bytebites.mobile.domain.state.commandFlow

interface UserLogoutProcess {

    fun logout(): Flow<CommandState>

}

class SessionTerminationProcess(
    private val authRepository: AuthRepository
) : UserLogoutProcess {

    override fun logout(): Flow<CommandState> =
        commandFlow {
            authRepository.logout()
        }

}

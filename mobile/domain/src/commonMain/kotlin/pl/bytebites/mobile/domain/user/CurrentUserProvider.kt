package pl.bytebites.mobile.domain.user

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.domain.model.User
import pl.bytebites.mobile.domain.repository.AuthRepository
import pl.bytebites.mobile.domain.state.QueryState
import pl.bytebites.mobile.domain.state.queryFlow

interface CurrentUserProvider {

    fun provide(): Flow<QueryState<User?>>

}

class RepositoryCurrentUserProvider(
    private val authRepository: AuthRepository
) : CurrentUserProvider {

    override fun provide(): Flow<QueryState<User?>> =
        queryFlow {
            authRepository.getCurrentUser()
        }

}

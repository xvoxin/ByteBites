package pl.bytebites.mobile.domain.provider

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.domain.model.User
import pl.bytebites.mobile.domain.repository.AuthRepository

class UserProvider(
    private val authRepository: AuthRepository
) {

    fun getCurrentUser(): Flow<User?> = authRepository.getCurrentUser()

}

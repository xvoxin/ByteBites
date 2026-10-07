package pl.bytebites.mobile.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.domain.model.User

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun login(email: String, password: String)
    suspend fun logout()
}

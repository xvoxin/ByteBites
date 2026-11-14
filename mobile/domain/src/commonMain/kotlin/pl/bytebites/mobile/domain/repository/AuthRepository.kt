package pl.bytebites.mobile.domain.repository

import pl.bytebites.mobile.domain.model.User

interface AuthRepository {

    suspend fun getCurrentUser(): Result<User?>

    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun logout(): Result<Unit>

}

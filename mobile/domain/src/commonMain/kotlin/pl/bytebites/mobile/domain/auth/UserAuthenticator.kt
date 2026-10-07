package pl.bytebites.mobile.domain.auth

import pl.bytebites.mobile.domain.repository.AuthRepository

class UserAuthenticator(
    private val authRepository: AuthRepository
) {
    suspend fun login(email: String, password: String): Result<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password cannot be empty."))
        }
        authRepository.login(email, password)
        return Result.success(Unit)
    }

    suspend fun logout(): Result<Unit> {
        authRepository.logout()
        return Result.success(Unit)
    }
}

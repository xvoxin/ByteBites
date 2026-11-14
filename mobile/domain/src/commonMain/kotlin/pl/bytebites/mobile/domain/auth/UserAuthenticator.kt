package pl.bytebites.mobile.domain.auth

import pl.bytebites.mobile.domain.repository.AuthRepository

class UserAuthenticator(
    private val authRepository: AuthRepository
) {
    suspend fun login(email: String, password: String): Result<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password cannot be empty."))
        }
        return runCatching {
            authRepository.login(email, password)
        }
    }

    suspend fun logout(): Result<Unit> = runCatching {
        authRepository.logout()
    }
}

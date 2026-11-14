package pl.bytebites.mobile.domain

import pl.bytebites.mobile.domain.model.User
import pl.bytebites.mobile.domain.repository.AuthRepository

class FakeAuthRepository(
    initialUser: User? = null
) : AuthRepository {

    var currentUser: User? = initialUser
    var currentUserResult: Result<User?>? = null
    var loginResult: Result<Unit> = Result.success(Unit)
    var logoutResult: Result<Unit> = Result.success(Unit)

    var logoutCallCount = 0

    override suspend fun getCurrentUser(): Result<User?> {
        return currentUserResult ?: Result.success(currentUser)
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return loginResult
    }

    override suspend fun logout(): Result<Unit> {
        logoutCallCount++
        val result = logoutResult
        if (result.isSuccess) {
            currentUser = null
        }
        return result
    }

}

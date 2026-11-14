package pl.bytebites.mobile.data.repository

import pl.bytebites.mobile.domain.model.User
import pl.bytebites.mobile.domain.repository.AuthRepository

class InMemoryAuthRepository : AuthRepository {

    private var currentUser: User? = defaultUser

    override suspend fun getCurrentUser(): Result<User?> {
        return Result.success(currentUser)
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        currentUser = User(
            id = "user_1",
            displayName = "Ania Kowalska",
            username = "ania_cooks",
            email = email
        )
        return Result.success(Unit)
    }

    override suspend fun logout(): Result<Unit> {
        currentUser = null
        return Result.success(Unit)
    }

    companion object {

        private val defaultUser = User(
            id = "user_1",
            displayName = "Ania Kowalska",
            username = "ania_cooks",
            email = "ania@bytebites.pl"
        )

    }

}

package pl.bytebites.mobile.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.bytebites.mobile.domain.model.User
import pl.bytebites.mobile.domain.repository.AuthRepository

class InMemoryAuthRepository : AuthRepository {

    private val userFlow = MutableStateFlow<User?>(defaultUser)

    override fun getCurrentUser(): Flow<User?> = userFlow.asStateFlow()

    override suspend fun login(email: String, password: String) {
        userFlow.value = User(
            id = "user_1",
            displayName = "Ania Kowalska",
            username = "ania_cooks",
            email = email
        )
    }

    override suspend fun logout() {
        userFlow.value = null
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

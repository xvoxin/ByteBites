package pl.bytebites.mobile.domain.model

data class User(
    val id: String,
    val displayName: String,
    val username: String,
    val email: String,
    val avatarUrl: String? = null
)

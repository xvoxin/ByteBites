package pl.bytebites.mobile.presentation.screen.profile.model

import pl.bytebites.mobile.domain.model.User as DomainUser

data class ProfileUser(
    val id: String,
    val displayName: String,
    val username: String,
    val email: String
)

fun DomainUser.toProfileUser(): ProfileUser = ProfileUser(
    id = id,
    displayName = displayName,
    username = "@$username",
    email = email
)

package pl.bytebites.mobile.presentation.screen.profile.model

data class ProfileState(
    val user: ProfileUser? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface ProfileIntent {

    data object LoginClicked : ProfileIntent

    data object LogoutClicked : ProfileIntent

}

sealed interface ProfileEffect {

    data object NavigateToAuth : ProfileEffect

}

package pl.bytebites.mobile.presentation.state

sealed interface CommandState {

    data object Success : CommandState

    data class Error(
        val throwable: Throwable
    ) : CommandState

}

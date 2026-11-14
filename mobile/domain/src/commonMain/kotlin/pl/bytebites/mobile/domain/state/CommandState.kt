package pl.bytebites.mobile.domain.state

sealed interface CommandState {

    data object Success : CommandState

    data class Error(val throwable: Throwable) : CommandState

}

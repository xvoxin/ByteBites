package pl.bytebites.mobile.presentation.error

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ErrorHandler {

    val errorDialog: StateFlow<ErrorDialogState?>

    fun showError(throwable: Throwable, title: String = "Błąd")

    fun dismissError()

}

class DefaultErrorHandler : ErrorHandler {

    override val errorDialog = MutableStateFlow<ErrorDialogState?>(null)

    override fun showError(throwable: Throwable, title: String) {
        if (throwable is CancellationException) return
        errorDialog.value = ErrorDialogState(
            title = title,
            message = throwable.message ?: "Nieoczekiwany błąd aplikacji",
            onDismiss = ::dismissError
        )
    }

    override fun dismissError() {
        errorDialog.value = null
    }

}

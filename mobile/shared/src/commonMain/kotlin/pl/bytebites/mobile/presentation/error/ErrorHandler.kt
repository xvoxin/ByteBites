package pl.bytebites.mobile.presentation.error

import bytebites.shared.generated.resources.Res
import bytebites.shared.generated.resources.error_default_message
import bytebites.shared.generated.resources.error_default_title
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pl.bytebites.mobile.presentation.resource.StringValue

interface ErrorHandler {

    val errorDialog: StateFlow<ErrorDialogState?>

    fun showError(throwable: Throwable, title: StringValue = StringValue.Resource(Res.string.error_default_title))

    fun showError(throwable: Throwable, title: String)

    fun dismissError()

}

class DefaultErrorHandler : ErrorHandler {

    override val errorDialog = MutableStateFlow<ErrorDialogState?>(null)

    override fun showError(throwable: Throwable, title: StringValue) {
        if (throwable is CancellationException) return
        val message = throwable.message?.takeIf { it.isNotBlank() }?.let { StringValue.Const(it) }
            ?: StringValue.Resource(Res.string.error_default_message)
        errorDialog.value = ErrorDialogState(
            title = title,
            message = message,
            onDismiss = ::dismissError
        )
    }

    override fun showError(throwable: Throwable, title: String) {
        val titleValue = title.takeIf { it.isNotBlank() }?.let { StringValue.Const(it) }
            ?: StringValue.Resource(Res.string.error_default_title)
        showError(throwable, titleValue)
    }

    override fun dismissError() {
        errorDialog.value = null
    }

}

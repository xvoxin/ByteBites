package pl.bytebites.mobile.presentation.error

data class ErrorDialogState(
    val title: String,
    val message: String,
    val onDismiss: () -> Unit = {}
)

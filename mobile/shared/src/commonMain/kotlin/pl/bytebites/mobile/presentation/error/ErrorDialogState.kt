package pl.bytebites.mobile.presentation.error

import pl.bytebites.mobile.presentation.resource.StringValue

data class ErrorDialogState(
    val title: StringValue,
    val message: StringValue,
    val onDismiss: () -> Unit = {}
)

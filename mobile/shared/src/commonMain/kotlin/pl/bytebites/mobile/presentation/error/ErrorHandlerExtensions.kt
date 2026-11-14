package pl.bytebites.mobile.presentation.error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bytebites.shared.generated.resources.Res
import bytebites.shared.generated.resources.error_default_title
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pl.bytebites.mobile.presentation.resource.StringValue

fun <VM> VM.launchSafe(
    title: StringValue = StringValue.Resource(Res.string.error_default_title),
    block: suspend CoroutineScope.() -> Unit
): Job where VM : ViewModel, VM : ErrorHandler = viewModelScope.launch {
    try {
        block()
    } catch (e: CancellationException) {
        throw e
    } catch (t: Throwable) {
        showError(t, title)
    }
}

fun <VM> VM.launchSafe(
    title: String,
    block: suspend CoroutineScope.() -> Unit
): Job where VM : ViewModel, VM : ErrorHandler = launchSafe(StringValue.Const(title), block)

fun ViewModel.launchSafe(
    errorHandler: ErrorHandler,
    title: StringValue = StringValue.Resource(Res.string.error_default_title),
    block: suspend CoroutineScope.() -> Unit
): Job = viewModelScope.launch {
    try {
        block()
    } catch (e: CancellationException) {
        throw e
    } catch (t: Throwable) {
        errorHandler.showError(t, title)
    }
}

fun ViewModel.launchSafe(
    errorHandler: ErrorHandler,
    title: String,
    block: suspend CoroutineScope.() -> Unit
): Job = launchSafe(errorHandler, StringValue.Const(title), block)

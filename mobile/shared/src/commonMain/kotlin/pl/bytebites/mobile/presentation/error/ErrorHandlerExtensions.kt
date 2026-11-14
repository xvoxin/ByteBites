package pl.bytebites.mobile.presentation.error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

fun <T> Flow<T>.catchToError(errorHandler: ErrorHandler, title: String = "Błąd"): Flow<T> =
    catch { throwable ->
        errorHandler.showError(throwable, title)
    }

fun ViewModel.launchSafe(
    errorHandler: ErrorHandler,
    title: String = "Błąd",
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

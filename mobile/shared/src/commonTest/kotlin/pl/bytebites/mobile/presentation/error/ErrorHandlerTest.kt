package pl.bytebites.mobile.presentation.error

import app.cash.turbine.test
import bytebites.shared.generated.resources.Res
import bytebites.shared.generated.resources.error_default_message
import bytebites.shared.generated.resources.error_default_title
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import pl.bytebites.mobile.presentation.resource.StringValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ErrorHandlerTest {

    private val errorHandler = DefaultErrorHandler()

    @Test
    fun `initial error dialog state is null`() = runTest {
        errorHandler.errorDialog.test {
            val state = awaitItem()
            assertNull(state, "Initial errorDialog state should be null")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `showError updates errorDialog state with title and exception message`() = runTest {
        errorHandler.errorDialog.test {
            assertEquals(null, awaitItem())

            val exception = IllegalStateException("Database connection failed")
            errorHandler.showError(exception, title = "Błąd bazy")

            val errorState = awaitItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Const("Błąd bazy"), errorState.title)
            assertEquals(StringValue.Const("Database connection failed"), errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `showError without title falls back to default resource title`() = runTest {
        errorHandler.errorDialog.test {
            assertEquals(null, awaitItem())

            errorHandler.showError(IllegalStateException("Something went wrong"))

            val errorState = awaitItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Resource(Res.string.error_default_title), errorState.title)
            assertEquals(StringValue.Const("Something went wrong"), errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `showError with empty message falls back to default resource message`() = runTest {
        errorHandler.errorDialog.test {
            assertEquals(null, awaitItem())

            errorHandler.showError(RuntimeException(""))

            val errorState = awaitItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Resource(Res.string.error_default_message), errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `showError with blank whitespace message falls back to default resource message`() = runTest {
        errorHandler.errorDialog.test {
            assertEquals(null, awaitItem())

            errorHandler.showError(RuntimeException("   "))

            val errorState = awaitItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Resource(Res.string.error_default_message), errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `showError with null message falls back to default resource message`() = runTest {
        errorHandler.errorDialog.test {
            assertEquals(null, awaitItem())

            errorHandler.showError(RuntimeException(null as String?))

            val errorState = awaitItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Resource(Res.string.error_default_message), errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `showError with blank title falls back to default resource title`() = runTest {
        errorHandler.errorDialog.test {
            assertEquals(null, awaitItem())

            errorHandler.showError(Exception("Test"), title = "   ")

            val errorState = awaitItem()
            assertNotNull(errorState)
            assertEquals(StringValue.Resource(Res.string.error_default_title), errorState.title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `showError ignores CancellationException and keeps state null`() = runTest {
        errorHandler.errorDialog.test {
            assertEquals(null, awaitItem())

            errorHandler.showError(CancellationException("Job was cancelled"))

            expectNoEvents()
        }
    }

    @Test
    fun `dismissError resets errorDialog state back to null`() = runTest {
        errorHandler.showError(Exception("Some error"))

        errorHandler.errorDialog.test {
            val activeError = awaitItem()
            assertNotNull(activeError)

            errorHandler.dismissError()

            val dismissedState = awaitItem()
            assertNull(dismissedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoking onDismiss on ErrorDialogState resets errorDialog state to null`() = runTest {
        errorHandler.showError(Exception("Some error"))

        errorHandler.errorDialog.test {
            val activeError = awaitItem()
            assertNotNull(activeError)

            activeError.onDismiss()

            val dismissedState = awaitItem()
            assertNull(dismissedState)
            cancelAndIgnoreRemainingEvents()
        }
    }

}

package pl.bytebites.mobile.domain.state

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> queryFlow(block: suspend () -> Result<T>): Flow<QueryState<T>> = 
    flow {
        try {
            block().fold(
                onSuccess = { emit(QueryState.Value(it)) },
                onFailure = { emit(QueryState.Error(it)) }
            )
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (throwable: Throwable) {
            emit(QueryState.Error(throwable))
        }
    }

fun commandFlow(block: suspend () -> Result<Unit>): Flow<CommandState> = 
    flow {
        try {
            block().fold(
                onSuccess = { emit(CommandState.Success) },
                onFailure = { emit(CommandState.Error(it)) }
            )
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (throwable: Throwable) {
            emit(CommandState.Error(throwable))
        }
    }

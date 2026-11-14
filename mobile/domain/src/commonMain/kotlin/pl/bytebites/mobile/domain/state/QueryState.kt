package pl.bytebites.mobile.domain.state

sealed interface QueryState<out T> {

    data class Value<out T>(
        val value: T
    ) : QueryState<T>

    data class Error(
        val throwable: Throwable
    ) : QueryState<Nothing>

}

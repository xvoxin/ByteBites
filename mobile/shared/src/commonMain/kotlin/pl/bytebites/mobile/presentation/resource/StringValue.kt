package pl.bytebites.mobile.presentation.resource

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface StringValue {

    data class Const(val value: String) : StringValue

    data class Resource(
        val res: StringResource,
        val args: List<Any> = emptyList()
    ) : StringValue

}

@Composable
fun StringValue.asString(): String = when (this) {
    is StringValue.Const -> value
    is StringValue.Resource -> if (args.isEmpty()) {
        stringResource(res)
    } else {
        stringResource(res, *args.toTypedArray())
    }
}

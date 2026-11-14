package pl.bytebites.mobile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList

class AppNavigator(
    val backStack: SnapshotStateList<NavKey> = mutableStateListOf(NavKey.Feed)
) {

    fun navigateTo(destination: NavKey) {
        backStack.add(destination)
    }

    fun navigateBack(): Boolean {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
            return true
        }
        return false
    }

}

@Composable
fun rememberAppNavigator(
    initialDestination: NavKey = NavKey.Feed
): AppNavigator {
    val backStack = rememberSaveable(
        saver = listSaver(
            save = { list -> list.map { key -> key.toRoute() } },
            restore = { saved ->
                mutableStateListOf<NavKey>().apply {
                    addAll(saved.map { route -> route.toNavKey() })
                }
            }
        )
    ) {
        mutableStateListOf(initialDestination)
    }

    return remember(backStack) { AppNavigator(backStack) }
}

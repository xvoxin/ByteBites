package pl.bytebites.mobile.presentation.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class AppNavigator(
    initialDestination: NavKey = NavKey.Feed
) {

    val backStack: SnapshotStateList<NavKey> = mutableStateListOf(initialDestination)

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

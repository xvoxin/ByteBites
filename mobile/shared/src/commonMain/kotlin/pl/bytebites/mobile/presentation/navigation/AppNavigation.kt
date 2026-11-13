package pl.bytebites.mobile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import pl.bytebites.mobile.presentation.screen.feed.view.FeedScreen
import pl.bytebites.mobile.presentation.screen.feed.viewmodel.FeedViewModel
import pl.bytebites.mobile.presentation.screen.recipe_detail.view.RecipeDetailScreen
import pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel.RecipeDetailViewModel

@Composable
fun AppNavigation(
    navigator: AppNavigator = koinInject(),
    modifier: Modifier = Modifier
) {
    val canGoBack = navigator.backStack.size > 1

    BackHandler(enabled = canGoBack) {
        navigator.navigateBack()
    }

    NavDisplay(
        backStack = navigator.backStack,
        modifier = modifier
    ) { key ->
        when (key) {
            is NavKey.Feed -> {
                val viewModel: FeedViewModel = koinInject()
                FeedScreen(
                    viewModel = viewModel,
                    onNavigateToDetail = { recipeId ->
                        navigator.navigateTo(NavKey.RecipeDetail(recipeId))
                    }
                )
            }

            is NavKey.RecipeDetail -> {
                val viewModel: RecipeDetailViewModel = koinInject()
                RecipeDetailScreen(
                    recipeId = key.recipeId,
                    viewModel = viewModel,
                    onNavigateBack = {
                        navigator.navigateBack()
                    }
                )
            }
        }
    }
}

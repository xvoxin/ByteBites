package pl.bytebites.mobile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import pl.bytebites.mobile.presentation.screen.feed.view.FeedScreen
import pl.bytebites.mobile.presentation.screen.feed.viewmodel.FeedViewModel
import pl.bytebites.mobile.presentation.screen.profile.view.ProfileScreen
import pl.bytebites.mobile.presentation.screen.profile.viewmodel.ProfileViewModel
import pl.bytebites.mobile.presentation.screen.recipe_detail.view.RecipeDetailScreen
import pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel.RecipeDetailViewModel

@Composable
fun AppNavigation(
    navigator: AppNavigator = rememberAppNavigator(),
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
                val viewModel: FeedViewModel = koinViewModel()
                FeedScreen(
                    viewModel = viewModel,
                    onNavigateToDetail = { recipeId ->
                        navigator.navigateTo(NavKey.RecipeDetail(recipeId))
                    },
                    onNavigateToProfile = {
                        navigator.navigateTo(NavKey.Profile)
                    }
                )
            }

            is NavKey.RecipeDetail -> {
                val viewModel: RecipeDetailViewModel = koinViewModel()
                RecipeDetailScreen(
                    recipeId = key.recipeId,
                    viewModel = viewModel,
                    onNavigateBack = {
                        navigator.navigateBack()
                    }
                )
            }

            is NavKey.Profile -> {
                val viewModel: ProfileViewModel = koinViewModel()
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navigator.navigateBack()
                    },
                    onNavigateToAuth = {
                        // Reserved for Step 4 Auth screen navigation
                    }
                )
            }
        }
    }
}

package pl.bytebites.mobile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import pl.bytebites.mobile.data.repository.di.repositoryModule
import pl.bytebites.mobile.domain.di.domainModule
import pl.bytebites.mobile.presentation.di.presentationModule
import pl.bytebites.mobile.presentation.feed.view.FeedScreen
import pl.bytebites.mobile.presentation.feed.viewmodel.FeedViewModel
import pl.bytebites.mobile.presentation.recipe_detail.view.RecipeDetailScreen
import pl.bytebites.mobile.presentation.recipe_detail.viewmodel.RecipeDetailViewModel
import pl.bytebites.mobile.presentation.theme.CookbookTheme

@Composable
@Preview
fun App() {

    KoinApplication(
        application = {
            modules(
                domainModule,
                repositoryModule,
                presentationModule
            )
        }
    ) {
        CookbookTheme {
            var selectedRecipeId by remember { mutableStateOf<String?>("1") }

            val currentId = selectedRecipeId
            if (currentId == null) {
                val feedViewModel: FeedViewModel = koinInject()
                FeedScreen(
                    viewModel = feedViewModel,
                    onNavigateToDetail = { recipeId -> selectedRecipeId = recipeId }
                )
            } else {
                val detailViewModel: RecipeDetailViewModel = koinInject()
                RecipeDetailScreen(
                    recipeId = currentId,
                    viewModel = detailViewModel,
                    onNavigateBack = { selectedRecipeId = null }
                )
            }
        }
    }

}

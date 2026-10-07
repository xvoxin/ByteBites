package pl.bytebites.mobile.presentation.feed.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import pl.bytebites.mobile.presentation.feed.model.FeedEffect
import pl.bytebites.mobile.presentation.feed.model.FeedIntent
import pl.bytebites.mobile.presentation.feed.model.FeedState
import pl.bytebites.mobile.presentation.feed.model.Recipe
import pl.bytebites.mobile.presentation.feed.viewmodel.FeedViewModel
import pl.bytebites.mobile.presentation.theme.CookbookTheme

private val PROGRESS_PADDING = 24.dp
private val LIST_PADDING_HORIZONTAL = 16.dp
private val LIST_PADDING_VERTICAL = 8.dp
private val SECTION_TITLE_PADDING_HORIZONTAL = 4.dp
private val SECTION_TITLE_PADDING_VERTICAL = 8.dp
private val CARD_SPACER_VERTICAL = 6.dp

@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    onNavigateToDetail: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is FeedEffect.NavigateToDetail -> onNavigateToDetail(effect.recipeId)
            }
        }
    }

    FeedContent(
        state = state,
        onIntent = viewModel::onIntent,
        modifier = modifier
    )
}

@Composable
fun FeedContent(
    state: FeedState,
    onIntent: (FeedIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            FeedHeader(totalRecipesCount = state.recipes.size)

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(PROGRESS_PADDING),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                state.error != null -> {
                    EmptyFeedState(message = state.error)
                }

                state.recipes.isEmpty() -> {
                    EmptyFeedState()
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            horizontal = LIST_PADDING_HORIZONTAL,
                            vertical = LIST_PADDING_VERTICAL
                        )
                    ) {
                        item {
                            Text(
                                text = "Wszystkie Przepisy",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier.padding(
                                    horizontal = SECTION_TITLE_PADDING_HORIZONTAL,
                                    vertical = SECTION_TITLE_PADDING_VERTICAL
                                )
                            )
                        }

                        items(
                            items = state.recipes,
                            key = { recipe -> recipe.id }
                        ) { recipe ->
                            Box(modifier = Modifier.padding(vertical = CARD_SPACER_VERTICAL)) {
                                RecipeListItemCard(
                                    recipe = recipe,
                                    onClick = { onIntent(FeedIntent.RecipeClicked(recipe.id)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- COMPOSE PREVIEWS ---

@Composable
@Preview
fun FeedContentSuccessPreview() {
    val sampleRecipes = listOf(
        Recipe(
            id = "1",
            title = "Kremowa Zupa Dyniowa",
            categoryName = "Obiady",
            prepTime = "30 min",
            difficulty = "Łatwe",
            rating = "4.9",
            emoji = "🎃"
        ),
        Recipe(
            id = "2",
            title = "Tarta Malinowa",
            categoryName = "Desery",
            prepTime = "45 min",
            difficulty = "Średnie",
            rating = "5.0",
            emoji = "🍰"
        )
    )

    CookbookTheme {
        FeedContent(
            state = FeedState(recipes = sampleRecipes),
            onIntent = {}
        )
    }
}

@Composable
@Preview
fun FeedContentLoadingPreview() {
    CookbookTheme {
        FeedContent(
            state = FeedState(isLoading = true),
            onIntent = {}
        )
    }
}

@Composable
@Preview
fun FeedContentEmptyPreview() {
    CookbookTheme {
        FeedContent(
            state = FeedState(recipes = emptyList()),
            onIntent = {}
        )
    }
}

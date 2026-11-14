package pl.bytebites.mobile.presentation.screen.recipe_detail.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.collectLatest
import pl.bytebites.mobile.presentation.navigation.BackTopBar
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetail
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailEffect
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailIntent
import pl.bytebites.mobile.presentation.screen.recipe_detail.model.RecipeDetailState
import pl.bytebites.mobile.presentation.screen.recipe_detail.viewmodel.RecipeDetailViewModel
import pl.bytebites.mobile.presentation.theme.CookbookTheme
import pl.bytebites.mobile.presentation.theme.Dimen

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    viewModel: RecipeDetailViewModel,
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.onIntent(RecipeDetailIntent.LoadRecipeDetail(recipeId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is RecipeDetailEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    RecipeDetailContent(
        state = state,
        onIntent = viewModel::onIntent,
        modifier = modifier
    )
}

@Composable
fun RecipeDetailContent(
    state: RecipeDetailState,
    onIntent: (RecipeDetailIntent) -> Unit,
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
            BackTopBar(
                title = "Szczegóły Przepisu",
                onBackClick = { onIntent(RecipeDetailIntent.BackClicked) }
            )

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(Dimen.Margin_3),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                state.error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(Dimen.Margin_4),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                state.recipeDetail != null -> {
                    val detail = state.recipeDetail

                    RecipeDetailHeader(detail = detail)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = Dimen.Margin_2_5, vertical = Dimen.Margin_1_5)
                    ) {
                        Text(
                            text = detail.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(Dimen.Margin_2))

                        RecipeDetailMetaRow(detail = detail)

                        Spacer(modifier = Modifier.height(Dimen.Margin_2))

                        IngredientsSection(ingredients = detail.ingredients)

                        Spacer(modifier = Modifier.height(Dimen.Margin_2))

                        StepsSection(steps = detail.steps)
                    }
                }
            }
        }
    }
}

// --- COMPOSE PREVIEWS ---

@Composable
@Preview
fun RecipeDetailContentSuccessPreview() {
    val sampleDetail = RecipeDetail(
        id = "1",
        title = "Kremowa Zupa Dyniowa z Imbirem",
        categoryName = "Obiady & Kolacje",
        prepTime = "30 min",
        difficulty = "Łatwe",
        rating = "4.9",
        servings = "4 porcje",
        description = "Aromatyczna, rozgrzewająca zupa krem z pieczonej dyni.",
        ingredients = listOf("Dynia hokkaido", "Mleczko kokosowe", "Imbir"),
        steps = listOf("Upiecz dynię", "Zblenduj z mleczkiem", "Podawaj z pestkami"),
        authorName = "ByteBites Chef"
    )

    CookbookTheme {
        RecipeDetailContent(
            state = RecipeDetailState(recipeDetail = sampleDetail, isLoading = false),
            onIntent = {}
        )
    }
}

@Composable
@Preview
fun RecipeDetailContentLoadingPreview() {
    CookbookTheme {
        RecipeDetailContent(
            state = RecipeDetailState(isLoading = true),
            onIntent = {}
        )
    }
}

@Composable
@Preview
fun RecipeDetailContentErrorPreview() {
    CookbookTheme {
        RecipeDetailContent(
            state = RecipeDetailState(error = "Nie znaleziono szczegółów przepisu", isLoading = false),
            onIntent = {}
        )
    }
}

package pl.bytebites.mobile.presentation.screen.recipe_detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import pl.bytebites.mobile.presentation.theme.Dimen

@Composable
fun IngredientsSection(
    ingredients: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Składniki",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(Dimen.Margin_1))

        Column(modifier.padding(start = Dimen.Margin_2)) {
            ingredients.forEach { ingredient ->
                IngredientBulletRow(ingredient = ingredient)
            }
        }
    }
}

@Composable
private fun IngredientBulletRow(ingredient: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimen.Margin_05),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.width(Dimen.Margin_1_25))

        Text(
            text = ingredient,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

package pl.bytebites.mobile.presentation.recipe_detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import pl.bytebites.mobile.presentation.recipe_detail.model.RecipeDetail
import pl.bytebites.mobile.presentation.theme.Dimen

@Composable
fun RecipeDetailMetaRow(
    detail: RecipeDetail,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.RadiusMedium))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(Dimen.Margin_1_5),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        MetaItem(label = "Czas", value = detail.prepTime, icon = "⏱️")
        MetaItem(label = "Trudność", value = detail.difficulty, icon = "🍳")
        MetaItem(label = "Porcje", value = detail.servings, icon = "👥")
        MetaItem(label = "Ocena", value = detail.rating, icon = "⭐")
    }
}

@Composable
private fun MetaItem(label: String, value: String, icon: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = icon, fontSize = Dimen.TextSize_2)
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = Dimen.TextSize_1)
        Text(
            text = label,
            fontSize = Dimen.TextSize_02,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

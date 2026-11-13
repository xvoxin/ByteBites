package pl.bytebites.mobile.presentation.screen.feed.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import bytebites.shared.generated.resources.Res
import bytebites.shared.generated.resources.restaurant
import bytebites.shared.generated.resources.star
import bytebites.shared.generated.resources.timer
import org.jetbrains.compose.resources.painterResource
import pl.bytebites.mobile.presentation.screen.feed.model.Recipe
import pl.bytebites.mobile.presentation.theme.Dimen

private const val BORDER_ALPHA = 0.5f
private const val EMOJI_CONTAINER_ALPHA = 0.6f

@Composable
fun RecipeListItemCard(
    recipe: Recipe,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(Dimen.RadiusLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(Dimen.BorderThin, MaterialTheme.colorScheme.outline.copy(alpha = BORDER_ALPHA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Margin_1_75),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(Dimen.EmblemBoxSize)
                    .clip(RoundedCornerShape(Dimen.RadiusMedium))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = EMOJI_CONTAINER_ALPHA)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.restaurant),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(Dimen.IconSizeMedium)
                )
            }

            Spacer(modifier = Modifier.width(Dimen.Margin_1_75))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = recipe.categoryName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(Dimen.Margin_075))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimen.Margin_1_25),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MetaItem(icon = painterResource(Res.drawable.timer), text = recipe.prepTime)
                    MetaItem(icon = painterResource(Res.drawable.restaurant), text = recipe.difficulty)
                    MetaItem(icon = painterResource(Res.drawable.star), text = recipe.rating)
                }
            }
        }
    }
}

@Composable
private fun MetaItem(icon: Painter, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(Dimen.IconSizeSmall)
        )
        Spacer(modifier = Modifier.width(Dimen.Margin_025))
        Text(
            text = text,
            fontSize = Dimen.TextSize_05,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}

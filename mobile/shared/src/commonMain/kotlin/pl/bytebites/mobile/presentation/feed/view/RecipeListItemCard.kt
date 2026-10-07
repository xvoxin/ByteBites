package pl.bytebites.mobile.presentation.feed.view

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.bytebites.mobile.presentation.feed.model.Recipe

private val CARD_CORNER_RADIUS = 16.dp
private val BORDER_THICKNESS = 1.dp
private val BORDER_ALPHA = 0.5f
private val CONTENT_PADDING = 14.dp
private val EMOJI_BOX_SIZE = 52.dp
private val EMOJI_BOX_CORNER_RADIUS = 14.dp
private val EMOJI_CONTAINER_ALPHA = 0.6f
private val EMOJI_FONT_SIZE = 28.sp
private val TITLE_SPACER_WIDTH = 14.dp
private val SUBTITLE_SPACER_HEIGHT = 6.dp
private val META_SPACING = 10.dp
private val META_ICON_FONT_SIZE = 11.sp
private val META_TEXT_FONT_SIZE = 11.sp
private val META_TEXT_SPACER_WIDTH = 3.dp

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
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(BORDER_THICKNESS, MaterialTheme.colorScheme.outline.copy(alpha = BORDER_ALPHA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CONTENT_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(EMOJI_BOX_SIZE)
                    .clip(RoundedCornerShape(EMOJI_BOX_CORNER_RADIUS))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = EMOJI_CONTAINER_ALPHA)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = recipe.emoji, fontSize = EMOJI_FONT_SIZE)
            }

            Spacer(modifier = Modifier.width(TITLE_SPACER_WIDTH))

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

                Spacer(modifier = Modifier.height(SUBTITLE_SPACER_HEIGHT))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(META_SPACING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MetaItem(icon = "⏱️", text = recipe.prepTime)
                    MetaItem(icon = "🍳", text = recipe.difficulty)
                    MetaItem(icon = "⭐", text = recipe.rating)
                }
            }
        }
    }
}

@Composable
private fun MetaItem(icon: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = icon, fontSize = META_ICON_FONT_SIZE)
        Spacer(modifier = Modifier.width(META_TEXT_SPACER_WIDTH))
        Text(
            text = text,
            fontSize = META_TEXT_FONT_SIZE,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}

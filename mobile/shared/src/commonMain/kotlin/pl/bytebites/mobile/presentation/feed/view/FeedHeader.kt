package pl.bytebites.mobile.presentation.feed.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HEADER_MARGIN = 16.dp
private val CARD_CORNER_RADIUS = 24.dp
private val HEADER_ELEVATION = 6.dp
private val CONTENT_PADDING = 20.dp
private val EMBLEM_SIZE = 56.dp
private val EMBLEM_EMOJI_SIZE = 28.sp
private val SPACER_HEIGHT = 10.dp
private val TITLE_LETTER_SPACING = 1.sp
private val SUBTITLE_LETTER_SPACING = 2.sp
private val SMALL_SPACER_HEIGHT = 4.dp
private val CONTAINER_ALPHA = 0.4f

@Composable
fun FeedHeader(
    totalRecipesCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(HEADER_MARGIN),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = HEADER_ELEVATION)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = CONTAINER_ALPHA))
                .padding(CONTENT_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(EMBLEM_SIZE)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📖", fontSize = EMBLEM_EMOJI_SIZE)
            }

            Spacer(modifier = Modifier.height(SPACER_HEIGHT))

            Text(
                text = "ByteBites",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = TITLE_LETTER_SPACING,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Text(
                text = "✦ KSIĄŻKA KUCHARSKA ✦",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = SUBTITLE_LETTER_SPACING,
                    color = MaterialTheme.colorScheme.secondary
                )
            )

            Spacer(modifier = Modifier.height(SMALL_SPACER_HEIGHT))

            Text(
                text = "$totalRecipesCount wspaniałych przepisów gotowych do odkrycia",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

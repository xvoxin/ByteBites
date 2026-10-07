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
import androidx.compose.ui.unit.sp
import pl.bytebites.mobile.presentation.theme.Dimen

private val TITLE_LETTER_SPACING = 1.sp
private val SUBTITLE_LETTER_SPACING = 2.sp
private const val CONTAINER_ALPHA = 0.4f

@Composable
fun FeedHeader(
    totalRecipesCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimen.Margin_2),
        shape = RoundedCornerShape(Dimen.RadiusXLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.ElevationHigh)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = CONTAINER_ALPHA))
                .padding(Dimen.Margin_2_5),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(Dimen.EmblemSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📖", fontSize = Dimen.TextSize_4_5)
            }

            Spacer(modifier = Modifier.height(Dimen.Margin_1_25))

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

            Spacer(modifier = Modifier.height(Dimen.Margin_05))

            Text(
                text = "$totalRecipesCount wspaniałych przepisów gotowych do odkrycia",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

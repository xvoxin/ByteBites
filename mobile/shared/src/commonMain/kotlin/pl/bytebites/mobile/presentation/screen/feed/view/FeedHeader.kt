package pl.bytebites.mobile.presentation.screen.feed.view

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bytebites.shared.generated.resources.Res
import bytebites.shared.generated.resources.person
import org.jetbrains.compose.resources.painterResource
import pl.bytebites.mobile.presentation.theme.CookbookTheme
import pl.bytebites.mobile.presentation.theme.Dimen

private val EMBLEM_SIZE = 56.dp

@Composable
fun FeedHeader(
    totalRecipesCount: Int,
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimen.Margin_2),
        shape = RoundedCornerShape(Dimen.RadiusXLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.ElevationMedium)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Margin_2_5)
        ) {
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.person),
                    contentDescription = "Mój Profil",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(Dimen.IconSizeMedium)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(EMBLEM_SIZE)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📖",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.Margin_1_25))

                Text(
                    text = "ByteBites",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                Text(
                    text = "✦ KSIĄŻKA KUCHARSKA ✦",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(Dimen.Margin_05))

                Text(
                    text = "$totalRecipesCount wspaniałych przepisów gotowych do odkrycia",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@Preview
fun FeedHeaderPreview() {
    CookbookTheme {
        Box(Modifier.background(Color.White)) {
            FeedHeader(
                totalRecipesCount = 42
            )
        }
    }
}

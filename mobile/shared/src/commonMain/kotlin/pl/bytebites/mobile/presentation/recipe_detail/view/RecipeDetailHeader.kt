package pl.bytebites.mobile.presentation.recipe_detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pl.bytebites.mobile.presentation.recipe_detail.model.RecipeDetail
import pl.bytebites.mobile.presentation.theme.ArrowBackIcon
import pl.bytebites.mobile.presentation.theme.Dimen
import pl.bytebites.mobile.presentation.theme.RestaurantIcon

private const val HEADER_CONTAINER_ALPHA = 0.4f
private val EMBLEM_CONTAINER_SIZE = 52.dp

@Composable
fun RecipeDetailHeader(
    detail: RecipeDetail,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.Margin_1, vertical = Dimen.Margin_05),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = ArrowBackIcon,
                    contentDescription = "Wróć",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "Szczegóły Przepisu",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.Margin_2, vertical = Dimen.Margin_05),
            shape = RoundedCornerShape(Dimen.RadiusXLarge),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimen.ElevationMedium)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = HEADER_CONTAINER_ALPHA))
                    .padding(Dimen.Margin_2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(EMBLEM_CONTAINER_SIZE)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = RestaurantIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(Dimen.IconSizeMedium)
                    )
                }

                Spacer(modifier = Modifier.width(Dimen.Margin_1_5))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = detail.categoryName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(Dimen.Margin_025))

                    Text(
                        text = detail.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

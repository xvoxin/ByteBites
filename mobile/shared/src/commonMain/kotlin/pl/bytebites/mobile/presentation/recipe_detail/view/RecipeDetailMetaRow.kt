package pl.bytebites.mobile.presentation.recipe_detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import bytebites.shared.generated.resources.chef_hat
import bytebites.shared.generated.resources.Res
import bytebites.shared.generated.resources.group
import bytebites.shared.generated.resources.star
import bytebites.shared.generated.resources.timer
import org.jetbrains.compose.resources.painterResource
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
        MetaItem(
            label = "Czas",
            value = detail.prepTime,
            icon = painterResource(Res.drawable.timer)
        )
        MetaItem(
            label = "Trudność",
            value = detail.difficulty,
            icon = painterResource(Res.drawable.chef_hat)
        )
        MetaItem(
            label = "Porcje",
            value = detail.servings,
            icon = painterResource(Res.drawable.group)
        )
        MetaItem(
            label = "Ocena",
            value = detail.rating,
            icon = painterResource(Res.drawable.star)
        )
    }
}

@Composable
private fun MetaItem(label: String, value: String, icon: Painter) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(Dimen.IconSizeMedium)
        )
        Spacer(modifier = Modifier.height(Dimen.Margin_025))
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = Dimen.TextSize_1)
        Text(
            text = label,
            fontSize = Dimen.TextSize_02,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

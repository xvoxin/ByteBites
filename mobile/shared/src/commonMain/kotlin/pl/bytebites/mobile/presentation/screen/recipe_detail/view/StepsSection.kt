package pl.bytebites.mobile.presentation.screen.recipe_detail.view

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.bytebites.mobile.presentation.theme.Dimen

private val STEP_BADGE_SIZE = 24.dp
private val STEP_TEXT_TOP_PADDING = 2.dp

@Composable
fun StepsSection(
    steps: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Sposób przygotowania",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(Dimen.Margin_1))

        steps.forEachIndexed { index, step ->
            StepItemRow(
                stepNumber = index + 1,
                stepText = step
            )
        }
    }
}

@Composable
private fun StepItemRow(
    stepNumber: Int,
    stepText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimen.Margin_075),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(STEP_BADGE_SIZE)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$stepNumber",
                color = Color.White,
                fontSize = Dimen.TextSize_05,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(Dimen.Margin_1_5))

        Text(
            text = stepText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .weight(1f)
                .padding(top = STEP_TEXT_TOP_PADDING)
        )
    }
}

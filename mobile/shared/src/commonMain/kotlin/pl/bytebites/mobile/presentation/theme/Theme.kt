package pl.bytebites.mobile.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = CookbookColor.TerracottaPrimary,
    onPrimary = CookbookColor.White,
    primaryContainer = CookbookColor.TerracottaContainer,
    onPrimaryContainer = CookbookColor.EspressoText,
    secondary = CookbookColor.ForestSageSecondary,
    onSecondary = CookbookColor.White,
    background = CookbookColor.ParchmentBackground,
    onBackground = CookbookColor.EspressoText,
    surface = CookbookColor.WarmPaperSurface,
    onSurface = CookbookColor.EspressoText,
    surfaceVariant = CookbookColor.WarmPaperSurface,
    onSurfaceVariant = CookbookColor.CharcoalVariant,
    outline = CookbookColor.BorderOutline
)

@Composable
fun CookbookTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}

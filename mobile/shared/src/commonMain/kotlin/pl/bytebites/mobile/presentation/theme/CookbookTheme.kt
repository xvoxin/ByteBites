package pl.bytebites.mobile.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TerracottaPrimary = Color(0xFFA83F24)
val TerracottaContainer = Color(0xFFFBECE5)
val ForestSageSecondary = Color(0xFF4E6B58)
val ParchmentBackground = Color(0xFFFAF6F0)
val WarmPaperSurface = Color(0xFFFFFDF9)
val EspressoOnSurface = Color(0xFF2C221E)
val CharcoalVariant = Color(0xFF6B5C53)
val BorderOutline = Color(0xFFDCD2C5)

val CookbookThemeColors = lightColorScheme(
    primary = TerracottaPrimary,
    onPrimary = Color.White,
    primaryContainer = TerracottaContainer,
    onPrimaryContainer = Color(0xFF421206),
    secondary = ForestSageSecondary,
    onSecondary = Color.White,
    background = ParchmentBackground,
    onBackground = EspressoOnSurface,
    surface = WarmPaperSurface,
    onSurface = EspressoOnSurface,
    surfaceVariant = Color(0xFFF1EBE1),
    onSurfaceVariant = CharcoalVariant,
    outline = BorderOutline
)

@Composable
fun CookbookTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CookbookThemeColors,
        content = content
    )
}

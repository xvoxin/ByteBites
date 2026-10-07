package pl.bytebites.mobile.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ArrowBackIcon: ImageVector
    get() {
        if (_arrowBack != null) return _arrowBack!!
        _arrowBack = ImageVector.Builder(
            name = "ArrowBack",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1.0f,
            strokeLineWidth = 1.0f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(20.0f, 11.0f)
            horizontalLineTo(7.83f)
            lineTo(13.42f, 5.41f)
            lineTo(12.0f, 4.0f)
            lineTo(4.0f, 12.0f)
            lineTo(12.0f, 20.0f)
            lineTo(13.41f, 18.59f)
            lineTo(7.83f, 13.0f)
            horizontalLineTo(20.0f)
            verticalLineTo(11.0f)
            close()
        }.build()
        return _arrowBack!!
    }

private var _arrowBack: ImageVector? = null

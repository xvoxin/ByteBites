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

val RestaurantIcon: ImageVector
    get() {
        if (_restaurant != null) return _restaurant!!
        _restaurant = ImageVector.Builder(
            name = "Restaurant",
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
            moveTo(11.0f, 9.0f)
            horizontalLineTo(9.0f)
            verticalLineTo(2.0f)
            horizontalLineTo(7.0f)
            verticalLineTo(9.0f)
            horizontalLineTo(5.0f)
            verticalLineTo(2.0f)
            horizontalLineTo(3.0f)
            verticalLineTo(9.0f)
            curveTo(3.0f, 11.21f, 4.79f, 13.0f, 7.0f, 13.0f)
            verticalLineTo(22.0f)
            horizontalLineTo(9.0f)
            verticalLineTo(13.0f)
            curveTo(11.21f, 13.0f, 13.0f, 11.21f, 13.0f, 9.0f)
            verticalLineTo(2.0f)
            horizontalLineTo(11.0f)
            verticalLineTo(9.0f)
            close()
            moveTo(16.0f, 6.0f)
            verticalLineTo(14.0f)
            horizontalLineTo(18.0f)
            verticalLineTo(22.0f)
            horizontalLineTo(20.0f)
            verticalLineTo(2.0f)
            curveTo(17.79f, 2.0f, 16.0f, 3.79f, 16.0f, 6.0f)
            close()
        }.build()
        return _restaurant!!
    }

private var _restaurant: ImageVector? = null

val ChefHatIcon: ImageVector
    get() {
        if (_chefHat != null) return _chefHat!!
        _chefHat = ImageVector.Builder(
            name = "ChefHat",
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
            moveTo(6.0f, 18.0f)
            horizontalLineTo(18.0f)
            verticalLineTo(20.0f)
            horizontalLineTo(6.0f)
            verticalLineTo(18.0f)
            close()
            moveTo(12.0f, 3.0f)
            curveTo(10.0f, 3.0f, 8.5f, 4.3f, 8.1f, 6.0f)
            curveTo(6.8f, 6.2f, 5.5f, 7.3f, 5.5f, 9.0f)
            curveTo(5.5f, 10.4f, 6.4f, 11.6f, 7.7f, 12.1f)
            lineTo(7.0f, 17.0f)
            horizontalLineTo(17.0f)
            lineTo(16.3f, 12.1f)
            curveTo(17.6f, 11.6f, 18.5f, 10.4f, 18.5f, 9.0f)
            curveTo(18.5f, 7.3f, 17.2f, 6.2f, 15.9f, 6.0f)
            curveTo(15.5f, 4.3f, 14.0f, 3.0f, 12.0f, 3.0f)
            close()
        }.build()
        return _chefHat!!
    }

private var _chefHat: ImageVector? = null

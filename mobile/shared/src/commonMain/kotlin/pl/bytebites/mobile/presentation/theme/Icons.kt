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

val TimerIcon: ImageVector
    get() {
        if (_timer != null) return _timer!!
        _timer = ImageVector.Builder(
            name = "Timer",
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
            moveTo(15.0f, 1.0f)
            horizontalLineTo(9.0f)
            verticalLineTo(3.0f)
            horizontalLineTo(15.0f)
            verticalLineTo(1.0f)
            close()
            moveTo(11.0f, 14.0f)
            horizontalLineTo(13.0f)
            verticalLineTo(8.0f)
            horizontalLineTo(11.0f)
            verticalLineTo(14.0f)
            close()
            moveTo(19.03f, 7.39f)
            lineTo(20.45f, 5.97f)
            curveTo(20.02f, 5.46f, 19.55f, 4.98f, 19.04f, 4.56f)
            lineTo(17.62f, 5.98f)
            curveTo(16.07f, 4.74f, 14.12f, 4.0f, 12.0f, 4.0f)
            curveTo(6.48f, 4.0f, 2.0f, 8.48f, 2.0f, 14.0f)
            curveTo(2.0f, 19.52f, 6.48f, 24.0f, 12.0f, 24.0f)
            curveTo(17.52f, 24.0f, 22.0f, 19.52f, 22.0f, 14.0f)
            curveTo(22.0f, 11.4f, 20.89f, 9.07f, 19.03f, 7.39f)
            close()
            moveTo(12.0f, 22.0f)
            curveTo(7.58f, 22.0f, 4.0f, 18.42f, 4.0f, 14.0f)
            curveTo(4.0f, 9.58f, 7.58f, 6.0f, 12.0f, 6.0f)
            curveTo(16.42f, 6.0f, 20.0f, 9.58f, 20.0f, 14.0f)
            curveTo(20.0f, 18.42f, 16.42f, 22.0f, 12.0f, 22.0f)
            close()
        }.build()
        return _timer!!
    }

private var _timer: ImageVector? = null

val StarIcon: ImageVector
    get() {
        if (_star != null) return _star!!
        _star = ImageVector.Builder(
            name = "Star",
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
            moveTo(12.0f, 17.27f)
            lineTo(18.18f, 21.0f)
            lineTo(16.54f, 13.97f)
            lineTo(22.0f, 9.24f)
            lineTo(14.81f, 8.63f)
            lineTo(12.0f, 2.0f)
            lineTo(9.19f, 8.63f)
            lineTo(2.0f, 9.24f)
            lineTo(7.46f, 13.97f)
            lineTo(5.82f, 21.0f)
            lineTo(12.0f, 17.27f)
            close()
        }.build()
        return _star!!
    }

private var _star: ImageVector? = null

val GroupIcon: ImageVector
    get() {
        if (_group != null) return _group!!
        _group = ImageVector.Builder(
            name = "Group",
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
            moveTo(16.0f, 11.0f)
            curveTo(17.66f, 11.0f, 18.99f, 9.66f, 18.99f, 8.0f)
            curveTo(18.99f, 6.34f, 17.66f, 5.0f, 16.0f, 5.0f)
            curveTo(14.34f, 5.0f, 13.0f, 6.34f, 13.0f, 8.0f)
            curveTo(13.0f, 9.66f, 14.34f, 11.0f, 16.0f, 11.0f)
            close()
            moveTo(8.0f, 11.0f)
            curveTo(9.66f, 11.0f, 10.99f, 9.66f, 10.99f, 8.0f)
            curveTo(10.99f, 6.34f, 9.66f, 5.0f, 8.0f, 5.0f)
            curveTo(6.34f, 5.0f, 5.0f, 6.34f, 5.0f, 8.0f)
            curveTo(5.0f, 9.66f, 6.34f, 11.0f, 8.0f, 11.0f)
            close()
            moveTo(8.0f, 13.0f)
            curveTo(5.33f, 13.0f, 0.0f, 14.34f, 0.0f, 17.0f)
            verticalLineTo(20.0f)
            horizontalLineTo(16.0f)
            verticalLineTo(17.0f)
            curveTo(16.0f, 14.34f, 10.67f, 13.0f, 8.0f, 13.0f)
            close()
            moveTo(16.0f, 13.0f)
            curveTo(15.71f, 13.0f, 15.38f, 13.02f, 15.03f, 13.05f)
            curveTo(16.2f, 13.9f, 17.0f, 15.08f, 17.0f, 16.5f)
            verticalLineTo(20.0f)
            horizontalLineTo(22.0f)
            verticalLineTo(17.0f)
            curveTo(22.0f, 14.83f, 18.25f, 13.43f, 16.0f, 13.0f)
            close()
        }.build()
        return _group!!
    }

private var _group: ImageVector? = null
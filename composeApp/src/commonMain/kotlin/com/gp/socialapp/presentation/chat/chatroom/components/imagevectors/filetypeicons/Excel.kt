package com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.FileTypeIcons

public val FileTypeIcons.Excel: ImageVector
    get() {
        if (_excel != null) {
            return _excel!!
        }
        _excel = Builder(name = "Excel", defaultWidth = 40.0.dp, defaultHeight = 40.0.dp,
                viewportWidth = 1024.0f, viewportHeight = 1024.0f).apply {
            path(fill = SolidColor(Color(0xFF00C090)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(116.9f, 0.0f)
                horizontalLineToRelative(552.1f)
                lineToRelative(262.8f, 257.7f)
                verticalLineTo(995.4f)
                curveToRelative(0.0f, 15.8f, -13.2f, 28.6f, -29.5f, 28.6f)
                horizontalLineTo(116.9f)
                curveToRelative(-16.3f, 0.0f, -29.5f, -12.8f, -29.5f, -28.6f)
                verticalLineTo(28.6f)
                curveTo(87.4f, 12.8f, 100.6f, 0.0f, 116.9f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF68DBBF)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(669.0f, 0.0f)
                verticalLineToRelative(229.1f)
                curveToRelative(0.0f, 15.8f, 13.2f, 28.6f, 29.5f, 28.6f)
                horizontalLineToRelative(233.3f)
                lineTo(669.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(703.7f, 370.6f)
                lineToRelative(-447.5f, 435.2f)
                arcToRelative(39.1f, 39.1f, 0.0f, false, false, 0.7f, 55.9f)
                curveToRelative(15.9f, 15.3f, 41.4f, 15.6f, 57.6f, 0.6f)
                lineToRelative(447.5f, -435.2f)
                curveToRelative(10.8f, -10.0f, 15.2f, -25.0f, 11.4f, -39.0f)
                curveToRelative(-3.8f, -14.0f, -15.1f, -25.0f, -29.6f, -28.6f)
                curveToRelative(-14.5f, -3.6f, -29.9f, 0.6f, -40.2f, 11.2f)
                horizontalLineToRelative(0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(256.2f, 426.0f)
                lineToRelative(447.5f, 435.2f)
                curveToRelative(16.2f, 15.0f, 41.8f, 14.8f, 57.6f, -0.6f)
                arcToRelative(39.1f, 39.1f, 0.0f, false, false, 0.7f, -55.9f)
                lineToRelative(-447.5f, -435.2f)
                curveToRelative(-16.2f, -15.0f, -41.8f, -14.8f, -57.6f, 0.6f)
                arcToRelative(39.1f, 39.1f, 0.0f, false, false, -0.7f, 55.9f)
                lineToRelative(-0.0f, 0.0f)
                close()
            }
        }
        .build()
        return _excel!!
    }

private var _excel: ImageVector? = null

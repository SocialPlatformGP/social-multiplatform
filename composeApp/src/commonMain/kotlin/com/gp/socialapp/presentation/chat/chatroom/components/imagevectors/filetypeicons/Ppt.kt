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

public val FileTypeIcons.Ppt: ImageVector
    get() {
        if (_ppt != null) {
            return _ppt!!
        }
        _ppt = Builder(name = "Ppt", defaultWidth = 40.0.dp, defaultHeight = 40.0.dp, viewportWidth
                = 1024.0f, viewportHeight = 1024.0f).apply {
            path(fill = SolidColor(Color(0xFFFF7861)), stroke = null, strokeLineWidth = 0.0f,
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
            path(fill = SolidColor(Color(0xFFFFB0A4)), stroke = null, strokeLineWidth = 0.0f,
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
                moveTo(695.6f, 473.3f)
                curveToRelative(9.2f, 9.1f, 14.0f, 22.2f, 14.0f, 41.6f)
                curveToRelative(0.0f, 19.4f, -4.8f, 32.5f, -14.0f, 41.6f)
                curveToRelative(-9.8f, 9.7f, -26.4f, 16.9f, -50.9f, 20.7f)
                horizontalLineToRelative(-364.0f)
                curveToRelative(-22.8f, 0.0f, -41.3f, 17.9f, -41.3f, 40.0f)
                verticalLineToRelative(179.4f)
                curveToRelative(0.0f, 14.3f, 7.9f, 27.5f, 20.6f, 34.7f)
                arcToRelative(42.4f, 42.4f, 0.0f, false, false, 41.2f, 0.0f)
                curveToRelative(12.8f, -7.1f, 20.6f, -20.4f, 20.6f, -34.7f)
                verticalLineToRelative(-139.4f)
                horizontalLineToRelative(325.8f)
                curveToRelative(1.9f, 0.0f, 3.9f, -0.1f, 5.8f, -0.4f)
                curveToRelative(88.1f, -12.2f, 138.5f, -62.2f, 138.5f, -141.9f)
                curveToRelative(0.0f, -79.7f, -50.4f, -129.7f, -138.5f, -141.9f)
                arcToRelative(42.0f, 42.0f, 0.0f, false, false, -5.8f, -0.4f)
                horizontalLineTo(280.7f)
                curveToRelative(-22.8f, 0.0f, -41.2f, 17.9f, -41.2f, 40.0f)
                curveToRelative(0.0f, 22.1f, 18.5f, 40.0f, 41.2f, 40.0f)
                horizontalLineToRelative(364.0f)
                curveToRelative(24.5f, 3.7f, 41.1f, 11.0f, 50.9f, 20.7f)
                verticalLineToRelative(0.0f)
                close()
            }
        }
        .build()
        return _ppt!!
    }

private var _ppt: ImageVector? = null

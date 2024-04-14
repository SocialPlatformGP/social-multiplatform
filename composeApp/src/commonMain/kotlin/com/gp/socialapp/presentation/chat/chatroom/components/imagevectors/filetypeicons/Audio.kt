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

public val FileTypeIcons.Audio: ImageVector
    get() {
        if (_audio != null) {
            return _audio!!
        }
        _audio = Builder(name = "Audio", defaultWidth = 40.0.dp, defaultHeight = 40.0.dp,
                viewportWidth = 1024.0f, viewportHeight = 1024.0f).apply {
            path(fill = SolidColor(Color(0xFFFF5562)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(137.9f, 0.0f)
                arcToRelative(48.6f, 48.6f, 0.0f, false, false, -35.6f, 15.3f)
                arcTo(54.0f, 54.0f, 0.0f, false, false, 87.0f, 50.9f)
                verticalLineTo(968.3f)
                arcToRelative(48.7f, 48.7f, 0.0f, false, false, 15.3f, 35.6f)
                arcToRelative(50.0f, 50.0f, 0.0f, false, false, 35.6f, 15.3f)
                horizontalLineToRelative(746.3f)
                arcToRelative(48.6f, 48.6f, 0.0f, false, false, 35.6f, -15.3f)
                arcToRelative(50.4f, 50.4f, 0.0f, false, false, 15.3f, -35.6f)
                verticalLineTo(288.7f)
                lineTo(646.7f, 0.0f)
                horizontalLineTo(137.9f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFBBC0)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(935.1f, 288.7f)
                horizontalLineToRelative(-237.4f)
                curveToRelative(-27.8f, -0.7f, -50.2f, -23.1f, -50.9f, -50.9f)
                verticalLineTo(0.0f)
                lineToRelative(288.4f, 288.7f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(678.1f, 487.5f)
                curveToRelative(0.4f, -2.2f, 0.4f, -4.5f, 0.0f, -6.7f)
                lineTo(678.1f, 358.5f)
                arcToRelative(18.1f, 18.1f, 0.0f, false, false, -5.9f, -12.8f)
                arcToRelative(15.9f, 15.9f, 0.0f, false, false, -13.6f, -3.4f)
                lineTo(393.0f, 417.1f)
                arcToRelative(15.4f, 15.4f, 0.0f, false, false, -12.7f, 15.3f)
                verticalLineToRelative(292.1f)
                arcToRelative(90.4f, 90.4f, 0.0f, false, false, -34.8f, -6.7f)
                curveToRelative(-9.4f, -0.1f, -18.8f, 1.0f, -28.0f, 3.4f)
                curveToRelative(-43.3f, 11.9f, -70.4f, 45.9f, -62.0f, 77.3f)
                curveToRelative(6.6f, 24.6f, 32.3f, 39.9f, 64.5f, 39.9f)
                curveToRelative(9.4f, -0.3f, 18.8f, -1.4f, 28.0f, -3.4f)
                curveToRelative(39.0f, -11.0f, 64.4f, -39.1f, 63.5f, -67.1f)
                curveToRelative(0.8f, -1.9f, 1.1f, -3.9f, 0.9f, -6.0f)
                verticalLineToRelative(-190.2f)
                lineToRelative(233.3f, -65.4f)
                verticalLineToRelative(144.4f)
                arcToRelative(90.7f, 90.7f, 0.0f, false, false, -34.8f, -6.7f)
                arcToRelative(106.1f, 106.1f, 0.0f, false, false, -28.0f, 3.4f)
                curveToRelative(-43.2f, 11.9f, -70.4f, 45.9f, -61.9f, 77.3f)
                curveToRelative(6.6f, 24.7f, 32.2f, 39.9f, 64.5f, 39.9f)
                curveToRelative(9.4f, 0.1f, 18.8f, -1.0f, 28.0f, -3.4f)
                curveToRelative(38.2f, -10.2f, 63.6f, -38.2f, 63.6f, -66.5f)
                curveToRelative(0.8f, -1.9f, 1.1f, -3.9f, 0.9f, -5.9f)
                lineToRelative(0.1f, -201.4f)
                close()
                moveTo(412.5f, 538.4f)
                lineTo(412.5f, 481.5f)
                lineToRelative(233.4f, -65.1f)
                verticalLineToRelative(56.9f)
                lineToRelative(-233.4f, 65.0f)
                close()
            }
        }
        .build()
        return _audio!!
    }

private var _audio: ImageVector? = null

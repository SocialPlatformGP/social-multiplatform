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

public val FileTypeIcons.Text: ImageVector
    get() {
        if (_text != null) {
            return _text!!
        }
        _text = Builder(name = "Text", defaultWidth = 40.0.dp, defaultHeight = 40.0.dp,
                viewportWidth = 1024.0f, viewportHeight = 1024.0f).apply {
            path(fill = SolidColor(Color(0xFFE5E5E5)), stroke = null, strokeLineWidth = 0.0f,
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
            path(fill = SolidColor(Color(0xFFCCCCCC)), stroke = null, strokeLineWidth = 0.0f,
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
                moveTo(248.1f, 365.2f)
                horizontalLineToRelative(220.5f)
                arcToRelative(25.5f, 25.5f, 0.0f, false, false, 24.2f, -25.5f)
                arcToRelative(25.5f, 25.5f, 0.0f, false, false, -24.2f, -25.5f)
                lineTo(248.1f, 314.2f)
                arcToRelative(25.5f, 25.5f, 0.0f, false, false, -24.2f, 25.5f)
                arcToRelative(25.5f, 25.5f, 0.0f, false, false, 24.2f, 25.5f)
                close()
                moveTo(248.1f, 535.0f)
                horizontalLineToRelative(525.8f)
                arcToRelative(25.4f, 25.4f, 0.0f, false, false, 25.4f, -25.5f)
                arcToRelative(25.4f, 25.4f, 0.0f, false, false, -25.4f, -25.5f)
                horizontalLineToRelative(-525.8f)
                arcToRelative(25.4f, 25.4f, 0.0f, false, false, -25.4f, 25.5f)
                arcToRelative(25.4f, 25.4f, 0.0f, false, false, 25.4f, 25.5f)
                close()
                moveTo(773.9f, 653.9f)
                horizontalLineToRelative(-525.8f)
                arcToRelative(25.5f, 25.5f, 0.0f, false, false, -24.2f, 25.5f)
                arcToRelative(25.5f, 25.5f, 0.0f, false, false, 24.2f, 25.5f)
                horizontalLineToRelative(525.8f)
                arcToRelative(25.5f, 25.5f, 0.0f, false, false, 24.2f, -25.5f)
                arcToRelative(25.5f, 25.5f, 0.0f, false, false, -24.2f, -25.5f)
                close()
            }
        }
        .build()
        return _text!!
    }

private var _text: ImageVector? = null

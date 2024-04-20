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

public val FileTypeIcons.Word: ImageVector
    get() {
        if (_word != null) {
            return _word!!
        }
        _word = Builder(name = "Word", defaultWidth = 40.0.dp, defaultHeight = 40.0.dp,
                viewportWidth = 1024.0f, viewportHeight = 1024.0f).apply {
            path(fill = SolidColor(Color(0xFF4A8DFF)), stroke = null, strokeLineWidth = 0.0f,
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
            path(fill = SolidColor(Color(0xFFE5F0FF)), stroke = null, strokeLineWidth = 0.0f,
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
                moveTo(722.8f, 823.5f)
                curveToRelative(25.4f, 27.0f, 71.7f, 9.6f, 71.7f, -27.0f)
                verticalLineTo(371.4f)
                horizontalLineToRelative(-82.5f)
                verticalLineToRelative(322.0f)
                lineToRelative(-164.0f, -174.2f)
                curveToRelative(-7.8f, -8.3f, -18.9f, -13.0f, -30.5f, -13.0f)
                curveToRelative(-11.6f, 0.0f, -22.6f, 4.7f, -30.5f, 13.0f)
                lineToRelative(-164.0f, 174.2f)
                verticalLineToRelative(-322.0f)
                horizontalLineTo(240.6f)
                verticalLineToRelative(425.1f)
                curveToRelative(0.0f, 36.5f, 46.3f, 53.9f, 71.7f, 27.0f)
                lineToRelative(205.2f, -218.0f)
                lineToRelative(205.2f, 218.0f)
                close()
            }
        }
        .build()
        return _word!!
    }

private var _word: ImageVector? = null

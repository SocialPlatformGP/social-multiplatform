package com.gp.socialapp.presentation.post.create

import androidx.compose.ui.graphics.Color


fun String.toColors(): Color {
    try {
        val color = this.replace("#", "").toLong(16)
        return Color(
            (color shr 16 and 0xFF) / 255f,
            (color shr 8 and 0xFF) / 255f,
            (color and 0xFF) / 255f,
            (color shr 24 and 0xFF) / 255f
        )
    } catch (e: Exception) {
        return Color.Black

    }
}

fun Color.toHex(): String {
    return "#$alpha$red$green$blue"
}



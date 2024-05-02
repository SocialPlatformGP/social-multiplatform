package com.gp.socialapp.util

import androidx.compose.runtime.Composable
import com.mohamedrejeb.calf.core.PlatformContext
import org.jetbrains.skiko.ClipboardManager

actual fun getPlatform(): Platform {
    return Platform.JS
}

actual fun PlatformContext.copyToClipboard(text: String) {
    val clipboardManager = ClipboardManager()
    clipboardManager.setText(text)
}
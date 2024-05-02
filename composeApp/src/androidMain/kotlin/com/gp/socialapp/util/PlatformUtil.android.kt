package com.gp.socialapp.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.mohamedrejeb.calf.core.PlatformContext
actual fun PlatformContext.copyToClipboard(text: String) {
    val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboardManager.setPrimaryClip(ClipData.newPlainText("text", text))
}
package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

actual fun Modifier.rowModifier(topPadding: Dp, onHoverChanged: (Boolean) -> Unit) = this.padding(
    top = topPadding
).fillMaxWidth()
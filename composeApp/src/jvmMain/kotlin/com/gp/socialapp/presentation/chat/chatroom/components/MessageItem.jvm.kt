package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.rowModifier(topPadding: Dp, onHoverChanged: (Boolean) -> Unit) = this.padding(
        top = topPadding
    ).fillMaxWidth().onPointerEvent(
        eventType = PointerEventType.Enter
    ){
        onHoverChanged(true)
    }.onPointerEvent(
        eventType = PointerEventType.Exit
    ){
        onHoverChanged(false)
}
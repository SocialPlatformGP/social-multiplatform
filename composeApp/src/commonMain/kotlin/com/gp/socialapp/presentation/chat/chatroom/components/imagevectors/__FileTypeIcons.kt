package com.gp.socialapp.presentation.chat.chatroom.components.imagevectors

import androidx.compose.ui.graphics.vector.ImageVector
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Audio
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Excel
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.File
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Pdf
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Ppt
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Text
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Video
import com.gp.socialapp.presentation.chat.chatroom.components.imagevectors.filetypeicons.Word
import kotlin.collections.List as ____KtList

public object FileTypeIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val FileTypeIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Audio, Excel, File, Pdf, Ppt, Text, Video, Word)
    return __AllIcons!!
  }

package com.gp.socialapp.presentation.chat.groupdetails.components.imagevectors

import androidx.compose.ui.graphics.vector.ImageVector
import com.gp.socialapp.presentation.chat.groupdetails.components.imagevectors.myiconpack.AddPeopleCircle
import kotlin.collections.List as ____KtList

public object MyIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val MyIconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons = listOf(AddPeopleCircle)
    return __AllIcons!!
  }

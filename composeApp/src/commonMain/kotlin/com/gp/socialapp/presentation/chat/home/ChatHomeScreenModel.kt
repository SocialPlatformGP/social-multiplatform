package com.gp.socialapp.presentation.chat.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChatHomeScreenModel(

) : ScreenModel {
    private val state = MutableStateFlow(ChatHomeUiState())
    val uiState = state


    fun getRecentRooms() {
        screenModelScope.launch {

        }
    }


}
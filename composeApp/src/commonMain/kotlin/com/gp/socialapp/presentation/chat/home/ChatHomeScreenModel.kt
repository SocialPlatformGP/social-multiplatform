package com.gp.socialapp.presentation.chat.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.chat.repository.RecentRoomRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChatHomeScreenModel(
    private val recentRoomRepository: RecentRoomRepository,
    private val authenticationRepository: AuthenticationRepository
) : ScreenModel {
    private val state = MutableStateFlow(ChatHomeUiState())
    val uiState = state

    init {
        getRecentRooms()
    }


    fun getRecentRooms() {
        val currentUserId = authenticationRepository.getCurrentLocalUserId()
        println("\n Current User Id: $currentUserId \n")
        screenModelScope.launch {
            recentRoomRepository.getAllRecentRooms(currentUserId)
                .collect {
                    when (it) {
                        is Result.SuccessWithData -> {
                            state.value = ChatHomeUiState(recentRooms = it.data)
                        }

                        is Result.Error -> {
                            println("\n Error: ${it.message} \n")
                        }

                        else -> Unit
                    }
                }
        }
    }


}
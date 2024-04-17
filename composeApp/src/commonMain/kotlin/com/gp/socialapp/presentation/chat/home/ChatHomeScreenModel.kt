package com.gp.socialapp.presentation.chat.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.chat.repository.RecentRoomRepository
import com.gp.socialapp.util.DispatcherIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatHomeScreenModel(
    private val recentRoomRepository: RecentRoomRepository,
    private val authenticationRepository: AuthenticationRepository
) : ScreenModel {
    private val state = MutableStateFlow(ChatHomeUiState())
    val uiState = state.asStateFlow()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        screenModelScope.launch(DispatcherIO) {
//            authenticationRepository.setLocalUserId("6616abe8ac5070037ba8b0d3")
            val userId = authenticationRepository.getCurrentLocalUserId()
            println("UserId: $userId")
            state.update {
                it.copy(currentUserId = userId)
            }
            getRecentRooms()
        }
    }


    fun getRecentRooms() {
        screenModelScope.launch(DispatcherIO) {
            recentRoomRepository.getAllRecentRooms(uiState.value.currentUserId)
                .collect { result ->
                    result.onSuccessWithData { data ->
                        state.value = ChatHomeUiState(recentRooms = data)
                    }.onFailure {
                        println("Error: $it")
                    }
                }
        }
    }
}
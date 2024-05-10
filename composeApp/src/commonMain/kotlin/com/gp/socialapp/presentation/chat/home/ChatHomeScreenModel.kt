package com.gp.socialapp.presentation.chat.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.chat.repository.RecentRoomRepository
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Job
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
    var job: Job? = null


    init {
        getCurrentUser()
    }


    fun getCurrentUser() {
        screenModelScope.launch(DispatcherIO) {
            authenticationRepository.getSignedInUser().let { result ->
                when(result) {
                    is Result.Success -> {
                        state.update {
                            it.copy(currentUserId = result.data.id)
                        }
                        getRecentRooms()
                        connectToSocket()
                    }
                    is Result.Error -> {
                        // TODO Handle error
                        Napier.d("Error: ${result.message}")
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun connectToSocket() {
        screenModelScope.launch(DispatcherIO) {
            val result = recentRoomRepository.connectToSocket(
                uiState.value.currentUserId,
            )
                when (result) {
                    is Result.Success -> {
                        println("Socket connected")
                    }
                    is Result.Error -> {
                        println("Socket connection failed")
                    }
                    else -> Unit
                }
        }
    }

    fun observeMessages() {
        if (job == null) {
            job = screenModelScope.launch {
                recentRoomRepository.observeNewData().collect { result ->
                    when (result) {
                        is Result.Success -> {
                            state.update {
                            it.copy(recentRooms = result.data.recentRooms.sortedByDescending { it.lastMessageTime })
                        }
                        }
                        is Result.Error -> {
                            println("Error: ${result.message}")
                        }
                        else -> Unit
                    }
                }
            }

        }
    }


    suspend fun getRecentRooms() {
        screenModelScope.launch(DispatcherIO) {
            recentRoomRepository.getAllRecentRooms(uiState.value.currentUserId)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            state.value =
                            ChatHomeUiState(recentRooms = result.data.sortedByDescending { it.lastMessageTime })
                        }
                        is Result.Error -> {
                            println("Error: ${result.message}")
                        }
                        else -> Unit
                    }
                }
            observeMessages()
        }
    }

    fun onClear() {
        screenModelScope.launch(DispatcherIO) {
            val result = recentRoomRepository.closeSocket()
                when (result) {
                    is Result.Success -> {
                        println("Socket closed")
                    }
                    is Result.Error -> {
                        println("Socket close failed")
                    }
                    else -> Unit
                }
        }
    }

    override fun onDispose() {
        super.onDispose()
        println("Disposed")
    }


}
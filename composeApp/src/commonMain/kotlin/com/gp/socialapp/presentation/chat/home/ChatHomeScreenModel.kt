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
            authenticationRepository.getSignedInUser().collect { result ->
                when(result) {
                    is Result.SuccessWithData -> {
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
            recentRoomRepository.connectToSocket(
                uiState.value.currentUserId,
            ).onSuccess {
                println("Socket connected")
            }.onFailure {
                println("Socket connection failed")
            }
        }
    }

    //    suspend fun observeMessages() {
//        recentRoomRepository.observeNewData().onEach { result ->
//            result.onSuccessWithData { newData ->
//                state.update {
//                    it.copy(recentRooms = newData.recentRooms)
//                }
//            }
//        }.launchIn(screenModelScope)
//
//    }
    fun observeMessages() {
        if (job == null) {
            job = screenModelScope.launch {
                recentRoomRepository.observeNewData().collect { result ->
                    println("im in home vm ")
                    result.onSuccessWithData { newData ->
                        state.update {
                            it.copy(recentRooms = newData.recentRooms.sortedByDescending { it.lastMessageTime })
                        }
                    }
                }
            }

        }
    }


    suspend fun getRecentRooms() {
        screenModelScope.launch(DispatcherIO) {
            recentRoomRepository.getAllRecentRooms(uiState.value.currentUserId)
                .collect { result ->
                    result.onSuccessWithData { data ->
                        state.value =
                            ChatHomeUiState(recentRooms = data.sortedByDescending { it.lastMessageTime })
                    }.onFailure {
                        println("Error: $it")
                    }
                }
            observeMessages()
        }
    }

    fun onClear() {
        screenModelScope.launch(DispatcherIO) {
            recentRoomRepository.closeSocket().onSuccess {
                println("Socket closed")
            }.onFailure {
                println("Socket close failed")
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        println("Disposed")
    }


}
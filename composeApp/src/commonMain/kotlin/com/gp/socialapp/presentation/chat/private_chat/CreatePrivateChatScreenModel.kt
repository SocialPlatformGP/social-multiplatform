package com.gp.socialapp.presentation.chat.private_chat

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePrivateChatScreenModel(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository,
    private val authenticationRepository: AuthenticationRepository

) : ScreenModel {
    private val state = MutableStateFlow(CreatePrivateChatUiState())
    val uiState = state

    init {
        getUsers()
        getCurrentUser()
    }

    private fun getCurrentUser() {
        val currentUser = authenticationRepository.getCurrentLocalUserId()
        state.update { oldState ->
            oldState.copy(
                currentUser = currentUser
            )
        }
    }

    fun getUsers() {
        screenModelScope.launch {
            userRepository.fetchUsers().collect { result ->
                when (result) {
                    is Result.SuccessWithData -> {
                        state.update {
                            it.copy(users = result.data)
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

    fun onUserSelected(user: User) {
        screenModelScope.launch {
            roomRepository.checkIfRoomExists(state.value.currentUser, user.id).collect {
                when (it) {
                    is Result.SuccessWithData -> {
                        println("*********Room Exists: ${it.data}")
                        state.update { oldState ->
                            oldState.copy(
                                room = it.data
                            )
                        }
                    }

                    is Result.Error -> {
                        println("*********Error: ${it.message}")
                    }

                    else -> Unit
                }
            }
        }
    }
}
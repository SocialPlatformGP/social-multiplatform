package com.gp.socialapp.presentation.chat.createprivate

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
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
        screenModelScope.launch (DispatcherIO) {
            authenticationRepository.getSignedInUser().let{ result ->
                when(result) {
                    is Result.SuccessWithData -> {
                        setCurrentUserId(result.data.id)
                    }
                    is Result.Error -> {
                        //TODO: Handle error
                        println("Error: ${result.message}")
                    }
                    else -> Unit
                }
            }

        }
    }

    private fun setCurrentUserId(id: String) {
        state.update { oldState ->
            oldState.copy(
                currentUserId = id
            )
        }
    }

    private fun getUsers() {
        screenModelScope.launch {
            userRepository.fetchUsers().collect { result ->
                result.onSuccessWithData { data ->
                    val users = data.filter { it.id != state.value.currentUserId }
                    state.update {
                        it.copy(allUsers = users, matchingUsers = users)
                    }
                }.onFailure {
                    println("Error: $it")
                }
            }
        }
    }

    fun onUserSelected(user: User) {
        screenModelScope.launch {
            roomRepository.checkIfRoomExists(state.value.currentUserId, user.id).collect { result ->
                result.onSuccessWithData { data ->
                    state.update { oldState ->
                        oldState.copy(
                            room = data
                        )
                    }
                }.onFailure {
                    println("Error: $it")
                }
            }
        }
    }

    fun clear() {
        state.value = CreatePrivateChatUiState()
    }

    fun onSearchQueryChanged(s: String) {
        screenModelScope.launch (Dispatchers.Default) {
            state.update { oldState ->
                oldState.copy(
                    matchingUsers = oldState.allUsers.filter { (it.name).contains(s, ignoreCase = true) }
                )
            }
        }
    }
}
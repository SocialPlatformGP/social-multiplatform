package com.gp.socialapp.presentation.chat.groupdetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GroupDetailsScreenModel(
    private val authRepo: AuthenticationRepository,
    private val roomRepo: RoomRepository,
    private val userRepo: UserRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(GroupDetailsUiState())
    val uiState = _uiState.asStateFlow()
    private lateinit var roomId: String
    fun init(roomId: String, roomTitle: String, roomAvatarUrl: String) {
        _uiState.value = GroupDetailsUiState(
            groupName = roomTitle,
            groupAvatarUrl = roomAvatarUrl
        )
        this.roomId = roomId
        getUserID()
        initGroupDetails()
    }

    private fun getUserID() {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                when(result){
                    is Result.SuccessWithData -> {
                        _uiState.update {
                            it.copy(currentUserId = result.data.id)
                        }
                    }
                    is Result.Error -> {
                        //todo handle error
                        Napier.d("error: ${result.message}")
                    }
                    else -> Unit
                }
            }

        }
    }

    private fun initGroupDetails() {
        screenModelScope.launch(DispatcherIO) {
            roomRepo.getRoomDetails(roomId).onSuccessWithData { room ->
                println("room: $room")
                val userIds = room.members.keys
                launch {
                    userRepo.getUsersByIds(userIds.toList()).collect { result ->
                        when (result) {
                            is Results.Failure -> {
                                //todo handle error
                                println("error: ${result.error}")
                            }

                            Results.Loading -> {
                                //todo handle loading
                            }

                            is Results.Success -> {
                                _uiState.update { state ->
                                    state.copy(
                                        members = result.data,
                                        admins = room.members.filter { it.value }.keys.toList(),
                                    )
                                }
                            }
                        }
                    }
                }
                _uiState.update { state ->
                    state.copy(
                        isAdmin = room.members[_uiState.value.currentUserId] ?: false
                    )
                }
            }.onFailure {
                //todo handle error
                println("error: $it")
            }
        }
    }

    fun handleUiAction(action: GroupDetailsAction) {
        when (action) {
            is GroupDetailsAction.OnChangeAvatar -> {
                changeAvatar(action.byteArray)
            }

            is GroupDetailsAction.OnChangeName -> {
                changeName(action.name)
            }

            is GroupDetailsAction.OnRemoveMember -> {
                removeMember(action.userId)
            }

            is GroupDetailsAction.OnMessageUser -> {
                onMessageUser(action.userId)
            }

            else -> Unit
        }
    }
    private fun onMessageUser(userId: String) {
        screenModelScope.launch {
            roomRepo.checkIfRoomExists(_uiState.value.currentUserId, userId).collect { result ->
                result.onSuccessWithData { data ->
                    _uiState.update { oldState ->
                        oldState.copy(
                            privateRoom = data
                        )
                    }
                }.onFailure {
                    println("Error: $it")
                }
            }
        }
    }
    private fun removeMember(userId: String) {
        screenModelScope.launch(DispatcherIO) {
            roomRepo.removeMember(roomId, userId).onSuccess {
                _uiState.update {
                    it.copy(members = it.members.filter { user -> user.id != userId })
                }
            }.onFailure {
                //todo handle error
            }
        }
    }

    private fun changeName(name: String) {
        screenModelScope.launch(DispatcherIO) {
            roomRepo.updateRoomName(roomId, name).onSuccess {
                _uiState.update { state ->
                    state.copy(groupName = name)
                }
            }.onFailure {
                //todo handle error
            }
        }
    }

    private fun changeAvatar(byteArray: ByteArray) {
        screenModelScope.launch(DispatcherIO) {
            roomRepo.updateRoomAvatar(roomId, byteArray).onSuccessWithData { url ->
                _uiState.update { state ->
                    state.copy(groupAvatarUrl = url)
                }
            }.onFailure {
                //todo handle error
            }
        }
    }
    fun dispose() {
        _uiState.value = GroupDetailsUiState()
    }
}
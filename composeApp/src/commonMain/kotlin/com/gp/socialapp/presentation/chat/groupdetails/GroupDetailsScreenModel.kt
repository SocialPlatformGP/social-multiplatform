package com.gp.socialapp.presentation.chat.groupdetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
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
                    is Result.Success -> {
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
            val result1 =roomRepo.getRoomDetails(roomId)
                when(result1){
                    is Result.Success -> {
                val userIds = result1.data.members.keys
                launch {
                    userRepo.getUsersByIds(userIds.toList()).collect { result ->
                        when (result) {
                            is Result.Error -> {
                                //todo handle error
                                println("error: ${result.message}")
                            }

                            Result.Loading -> {
                                //todo handle loading
                            }

                            is Result.Success -> {
                                _uiState.update { state ->
                                    state.copy(
                                        members = result.data,
                                        admins = result1.data.members.filter { it.value }.keys.toList(),
                                    )
                                }
                            }
                        }
                    }
                }
                _uiState.update { state ->
                    state.copy(
                        isAdmin = result1.data.members[_uiState.value.currentUserId] ?: false
                    )
                }
                    }
                    is Result.Error -> {
                        //todo handle error
                    }
                    else -> Unit
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
                when (result) {
                    is Result.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                privateRoom = result.data
                            )
                        }
                    }
                    is Result.Error -> {
                        //todo handle error
                    }
                    else -> Unit
                }
            }
        }
    }
    private fun removeMember(userId: String) {
        screenModelScope.launch(DispatcherIO) {
            val result = roomRepo.removeMember(roomId, userId)
                when(result){
                    is Result.Error -> TODO()
                    Result.Loading -> TODO()
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(members = it.members.filter { user -> user.id != userId })
                        }
                    }
                }
        }
    }

    private fun changeName(name: String) {
        screenModelScope.launch(DispatcherIO) {
            val  result = roomRepo.updateRoomName(roomId, name)
                when(result){
                    is Result.Error -> {
                        //todo handle error
                    }
                    Result.Loading -> {
                        //todo handle loading
                    }
                    is Result.Success -> {
                        _uiState.update { state ->
                            state.copy(groupName = name)
                        }
                    }
                }
        }
    }

    private fun changeAvatar(byteArray: ByteArray) {
        screenModelScope.launch(DispatcherIO) {
           val result =  roomRepo.updateRoomAvatar(roomId, byteArray)
               when(result){
                   is Result.Error -> {
                       //todo handle error
                   }
                   Result.Loading -> {
                       //todo handle loading
                   }
                   is Result.Success -> {
                      _uiState.update { state ->
                    state.copy(groupAvatarUrl = result.data)
                }
                   }
               }
        }
    }
    fun dispose() {
        _uiState.value = GroupDetailsUiState()
    }
}
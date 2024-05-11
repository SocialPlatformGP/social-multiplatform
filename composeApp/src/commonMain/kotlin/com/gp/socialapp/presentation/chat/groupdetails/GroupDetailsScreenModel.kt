package com.gp.socialapp.presentation.chat.groupdetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class GroupDetailsScreenModel(
    private val authRepo: AuthenticationRepository,
    private val roomRepo: RoomRepository,
    private val userRepo: UserRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(GroupDetailsUiState())
    val uiState = _uiState.asStateFlow()
    private var roomId by Delegates.notNull<Long>()
    fun init(roomId: Long, roomTitle: String, roomAvatarUrl: String) {
        _uiState.value = GroupDetailsUiState(
            groupName = roomTitle, groupAvatarUrl = roomAvatarUrl
        )
        this.roomId = roomId
        getUserID()
        initGroupDetails()
    }

    private fun getUserID() {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.SuccessWithData -> {
                        _uiState.update {
                            it.copy(currentUser = result.data)
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
            roomRepo.getRoom(roomId).onSuccessWithData { room ->
                println("room: $room")
                val userIds = room.members.keys
                _uiState.update { state ->
                    state.copy(
                        isAdmin = room.members[_uiState.value.currentUser.id] ?: false
                    )
                }
                getGroupUsers(userIds, room.members)
            }.onFailure {
                //todo handle error
                println("error: $it")
            }
        }
    }

    private fun getGroupUsers(userIds: Set<String>, roomMembers: Map<String, Boolean>) {
        screenModelScope.launch(DispatcherIO) {
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
                                admins = roomMembers.filter { it.value }.keys.toList(),
                            )
                        }
                    }
                }
            }
        }
    }

    fun handleUiAction(action: GroupDetailsAction) {
        when (action) {
            is GroupDetailsAction.OnChangeAvatar -> {
                changeAvatar(action.byteArray, action.extension)
            }

            is GroupDetailsAction.OnChangeName -> {
                changeName(action.name)
            }

            is GroupDetailsAction.OnRemoveMember -> {
                removeMember(action.userId)
            }

            is GroupDetailsAction.OnMessageUser -> {
                onMessageUser(action.otherUser)
            }

            else -> Unit
        }
    }

    private fun onMessageUser(otherUser: User) {
        screenModelScope.launch(DispatcherIO) {
            roomRepo.getPrivateRoom(_uiState.value.currentUser, otherUser).let { result ->
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

    private fun changeAvatar(byteArray: ByteArray, extension: String) {
        screenModelScope.launch(DispatcherIO) {
            roomRepo.updateRoomAvatar(roomId, byteArray, extension).onSuccessWithData { url ->
                _uiState.update { state ->
                    state.copy(groupAvatarUrl = url)
                }
            }.onFailure {
                //todo handle error
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        _uiState.value = GroupDetailsUiState()
    }
}
package com.gp.socialapp.presentation.chat.creategroup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.presentation.chat.creategroup.SelectableUser.Companion.toSelectableUser
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateGroupScreenModel(
    private val authRepo: AuthenticationRepository,
    private val userRepo: UserRepository,
    private val roomRepo: RoomRepository,
) : ScreenModel {
    private lateinit var currentUserId: String
    private val _uiState = MutableStateFlow(CreateGroupUiState())
    val uiState = _uiState.asStateFlow()

    fun init() {
        resetState()
        getUsers()
    }

    private fun getUsers() {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let{ result ->
                when(result) {
                    is Result.Success -> {
                        currentUserId = result.data.id
                        getAllUsers()
                    }
                    is Result.Error -> {
                        //TODO handle error
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun getAllUsers() {
        screenModelScope.launch(DispatcherIO) {
            userRepo.fetchUsers().collect { result ->
                when (result) {
                    is Result.Success -> {
                        updateUsersListState(result.data)
                    }
                    is Result.Error -> {
                        //TODO handle error
                    }
                    else -> Unit
                }

            }
        }
    }

    private fun updateUsersListState(users: List<User>) {
        _uiState.update { state ->
            state.copy(allUsers = users.map { user ->
                user.toSelectableUser()
            }.filter {
                it.user.id != currentUserId
            })
        }
    }

    private fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(groupName = name)
    }

    private fun updateAvatar(byteArray: ByteArray) {
        _uiState.value = _uiState.value.copy(groupAvatarByteArray = byteArray)
    }

    private fun updateError(value: Boolean) {
        _uiState.value = _uiState.value.copy(isError = value)
    }

    private fun addMember(userId: String) {
        val user = _uiState.value.allUsers.first { it.user.id == userId }.user
        screenModelScope.launch(Dispatchers.Default) {
            val updatedMembers = _uiState.value.selectedUsers.toMutableList()
            updatedMembers.add(user)
            val updatedUsers = _uiState.value.allUsers.map {
                if (it.user.id == userId) {
                    it.copy(isSelected = true)
                } else {
                    it
                }
            }
            _uiState.update {
                it.copy(allUsers = updatedUsers, selectedUsers = updatedMembers)
            }
        }
    }

    private fun removeMember(userId: String) {
        val user = _uiState.value.selectedUsers.first { it.id == userId }
        screenModelScope.launch(Dispatchers.Default) {
            val updatedMembers = _uiState.value.selectedUsers.toMutableList()
            updatedMembers.remove(user)
            val updatedUsers = _uiState.value.allUsers.map {
                if (it.user.id == user.id) {
                    it.copy(isSelected = false)
                } else {
                    it
                }
            }
            _uiState.update {
                it.copy(allUsers = updatedUsers, selectedUsers = updatedMembers)
            }
            println("Selected Users: ${_uiState.value.selectedUsers}")
            println("All Users: ${_uiState.value.allUsers}")
        }
    }

    private fun createGroup() {
        screenModelScope.launch(DispatcherIO) {
            with(uiState.value) {
                roomRepo.createGroupRoom(
                    groupName = groupName,
                    groupAvatar = groupAvatarByteArray,
                    userIds = selectedUsers.map { it.id },
                    creatorId = currentUserId
                ).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.update {
                                it.copy(
                                    isCreated = true,
                                    groupId = result.data.id,
                                    groupAvatarUrl = result.data.picUrl
                                )
                            }
                        }
                        is Result.Error -> {
                            println("Error: $result")
                        }
                        else -> Unit
                    }

                }
            }
        }
    }

    fun handleUiAction(action: CreateGroupAction) {
        when (action) {
            is CreateGroupAction.OnUpdateName -> updateName(action.name)
            is CreateGroupAction.OnSetError -> updateError(action.value)
            is CreateGroupAction.OnUnselectUser -> removeMember(action.userId)
            is CreateGroupAction.OnCreateGroup -> createGroup()
            is CreateGroupAction.OnImagePicked -> updateAvatar(action.array)
            is CreateGroupAction.OnSelectUser -> addMember(action.userId)
            else -> Unit
        }
    }

    private fun resetState() {
        _uiState.value = CreateGroupUiState()
    }
}
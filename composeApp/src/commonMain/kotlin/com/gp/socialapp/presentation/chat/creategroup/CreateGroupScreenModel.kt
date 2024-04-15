package com.gp.socialapp.presentation.chat.creategroup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.presentation.chat.creategroup.SelectableUser.Companion.toSelectableUser
import com.gp.socialapp.util.DispatcherIO
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

    init {
        screenModelScope.launch(DispatcherIO) {
//            currentUserId = authRepo.getCurrentLocalUserId()
            currentUserId = "test-user-id"
            getAllUsers()
        }
    }

    private fun getAllUsers() {
        screenModelScope.launch(DispatcherIO) {
//            userRepo.fetchUsers().collect { result ->
//                result.onSuccessWithData { data ->
//                    updateUsersListState(data)
//                }.onFailure {
//                    updateError(true)
//                    println("Error: $it")
//                }
//            }
            val users = listOf(
                User("test-user-id", "test-user-name", "test-user-email", "test-user-avatar"),
                User("test-user-id-2", "test-user-name-2", "test-user-email-2", "test-user-avatar-2"),
                User("test-user-id-3", "test-user-name-3", "test-user-email-3", "test-user-avatar-3"),
            )
            updateUsersListState(users)
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
        _uiState.value = _uiState.value.copy(groupAvatar = byteArray)
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
            println("Selected Users: ${_uiState.value.selectedUsers}")
            println("All Users: ${_uiState.value.allUsers}")
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
                    groupAvatar = groupAvatar,
                    userIds = selectedUsers.map { it.id },
                    creatorId = currentUserId
                ).collect { result ->
                    result.onSuccessWithData { data ->
                        _uiState.update {
                            it.copy(isCreated = true, groupId = data)
                        }
                    }.onFailure {
                        println("Error: $it")
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
        }
    }
}
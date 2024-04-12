package com.gp.socialapp.presentation.chat.creategroup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.DispatcherIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateGroupScreenModel(
    authRepo: AuthenticationRepository,
    userRepo: UserRepository
): ScreenModel {
    private lateinit var currentUserId : String
    private val _uiState = MutableStateFlow(CreateGroupUiState())
    val uiState = _uiState.asStateFlow()
    init {
        screenModelScope.launch(DispatcherIO) {
            currentUserId = authRepo.getCurrentLocalUserId()
            getAllUsers()
        }
    }

    private fun getAllUsers() {
        TODO("Not yet implemented")
//        screenModelScope.launch(DispatcherIO) {
//            userRepo.fetchUsers().collect {
//                when (it) {
//                    is State.SuccessWithData -> {
//                        _users.value = it.data.map { it.toSelectableUser() }
//                            .filter { it.data.email != currentUserEmail }
//                        Log.d("EDREES", "User: ${_users.value.map { it.data.email }}")
//                    }
//
//                    is State.Error -> {
//                        Log.e("EDREES", "fetchUsers() error: ${it.message}")
//                    }
//
//                    else -> {
//                        Log.d("Edrees", "Loading from all users: ${it is State.Loading}")
//                    }
//                }
//            }
//            Log.d("EDREES", "After fetchUsers()")
//        }
    }
    private fun updateName(name: String) {
//        name.value = name
        _uiState.value = _uiState.value.copy(groupName = name)
    }

    private fun updateAvatarL(byteArray: ByteArray) {
        _uiState.value = _uiState.value.copy(groupAvatar = byteArray)
    }

    private fun addMember(user: User) {
        screenModelScope.launch(Dispatchers.Default) {
            val updatedMembers = _uiState.value.selectedUsers.toMutableList()
            updatedMembers.add(user)
            val updatedUsers = _uiState.value.allUsers.map {
                if (it.email == user.email) {
                    it
//                    it.copy(isSelected = true)TODO
                } else {
                    it
                }
            }
            _uiState.update {
                it.copy(allUsers = updatedUsers, selectedUsers = updatedMembers)
            }
        }
    }

    private fun removeMember(user: User) {
        screenModelScope.launch(Dispatchers.Default) {
            val updatedMembers = _uiState.value.selectedUsers.toMutableList()
            updatedMembers.remove(user)
            val updatedUsers = _uiState.value.allUsers.map {
                if (it.email == user.email) {
                    it
//                    it.copy(isSelected = true)TODO
                } else {
                    it
                }
            }
            _uiState.update {
                it.copy(allUsers = updatedUsers, selectedUsers = updatedMembers)
            }
        }
    }
    private fun createGroup() {
        screenModelScope.launch(DispatcherIO) {
//            chatRepo.createGroupChat(
//                name = _name.value,
//                avatarLink = _avatarURL.value,
//                members = _selectedUsers.value.map { it.email },
//                currentUserEmail = currentUserEmail
//            ).collect {
//                when (it) {
//                    is State.SuccessWithData -> {
//                        _groupID.value = it.data
//                        _isCreated.value = true
//                    }
//
//                    is State.Error -> {
//                        Log.d("SEERDE", "createGroup: ${it.message}")
//                    }
//
//                    is State.Loading -> {}
//                    else -> {}
//                }
//            }
        }
    }
}
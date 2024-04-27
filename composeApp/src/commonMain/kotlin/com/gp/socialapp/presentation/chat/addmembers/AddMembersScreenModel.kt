package com.gp.socialapp.presentation.chat.addmembers

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.presentation.chat.creategroup.SelectableUser.Companion.toSelectableUser
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddMembersScreenModel(
    private val authRepo: AuthenticationRepository,
    private val userRepo: UserRepository,
    private val roomRepo: RoomRepository
): ScreenModel {
    private val _uiState = MutableStateFlow(AddMembersUiState())
    val uiState = _uiState.asStateFlow()
    private lateinit var currentUserId: String
    private lateinit var roomId: String
    private lateinit var groupMembersIds: List<String>
    fun init(roomId: String, groupMembersIds: List<String>) {
        this.roomId = roomId
        this.groupMembersIds = groupMembersIds
        resetState()
        getUserID()
        initAddMembers()
    }

    private fun initAddMembers() {
        screenModelScope.launch (DispatcherIO) {
            userRepo.fetchUsers().collect { result ->
                when(result) {
                    is Result.Error -> {
                        //TODO handle error
                    }
                    is Result.Loading -> {
                        //TODO handle loading
                    }
                    is Result.SuccessWithData -> {
                        val allUsers = result.data.filter { it.id != currentUserId && !groupMembersIds.contains(it.id) }
                        _uiState.update {
                            it.copy(
                                allUsers = allUsers.map { user -> user.toSelectableUser() }
                            )
                        }
                        println("All Users: ${_uiState.value.allUsers}")
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun getUserID() {
        screenModelScope.launch (DispatcherIO) {
            authRepo.getSignedInUser().let{ result ->
                when(result) {
                    is Result.SuccessWithData -> {
                        currentUserId = result.data.id
                    }
                    is Result.Error -> {
                        //TODO handle error
                    }
                    else -> Unit
                }
            }
        }
    }

    fun removeMember(memberId: String) {
        println("Member ID: $memberId")
        val user = _uiState.value.selectedUsers.first { it.id == memberId }
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

    fun addMember(memberId: String) {
        println("Member ID: $memberId")
        val user = _uiState.value.allUsers.first { it.user.id == memberId }.user
        screenModelScope.launch(Dispatchers.Default) {
            val updatedMembers = _uiState.value.selectedUsers.toMutableList()
            updatedMembers.add(user)
            val updatedUsers = _uiState.value.allUsers.map {
                if (it.user.id == memberId) {
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

    fun submitGroupUsers() {
        screenModelScope.launch {
            val selectedUserIds = _uiState.value.selectedUsers.map { it.id }
            roomRepo.addMembers(roomId, selectedUserIds).onSuccess {
                _uiState.update { it.copy(isDone = true) }
            }.onFailure {
                //TODO handle error
            }
        }
    }

    private fun resetState() {
        _uiState.update { AddMembersUiState() }
    }
}
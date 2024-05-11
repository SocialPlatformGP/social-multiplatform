package com.gp.socialapp.presentation.chat.groupdetails

import com.gp.socialapp.data.auth.source.remote.model.User

sealed class GroupDetailsAction {
    data class OnChangeAvatar(val byteArray: ByteArray, val extension: String) :
        GroupDetailsAction()

    data class OnChangeName(val name: String) : GroupDetailsAction()
    data object OnAddMembersClicked : GroupDetailsAction()
    data object OnBackClicked : GroupDetailsAction()

    data class OnRemoveMember(val userId: String) : GroupDetailsAction()
    data class OnViewUserProfile(val userId: String) : GroupDetailsAction()
    data class OnMessageUser(val otherUser: User) : GroupDetailsAction()
}
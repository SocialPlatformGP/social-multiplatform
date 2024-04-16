package com.gp.socialapp.presentation.chat.groupdetails

sealed class GroupDetailsAction {
    data class OnChangeAvatar(val byteArray: ByteArray): GroupDetailsAction()
    data class OnChangeName(val name: String): GroupDetailsAction()
    data object OnAddMembersClicked: GroupDetailsAction()
    data class OnRemoveMember(val userId: String): GroupDetailsAction()
    data class OnViewUserProfile(val userId: String): GroupDetailsAction()
    data class OnMessageUser(val userId: String): GroupDetailsAction()
}
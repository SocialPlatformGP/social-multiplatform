package com.gp.socialapp.presentation.community.communitymembers

sealed class CommunityMembersUiAction {
    data class OnAcceptRequest(val userId: String) : CommunityMembersUiAction()
    data class OnDeclineRequest(val userId: String) : CommunityMembersUiAction()
    data class OnUserClicked(val userId: String) : CommunityMembersUiAction()
    data object OnBackClicked : CommunityMembersUiAction()
}
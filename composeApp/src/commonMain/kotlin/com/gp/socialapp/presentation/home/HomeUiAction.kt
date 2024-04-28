package com.gp.socialapp.presentation.home

import com.gp.socialapp.data.community.source.remote.model.Community

sealed interface HomeUiAction {
    data class OnCommunityClicked(val communityId: String) : HomeUiAction
    data class OnCommunityLogout(val id: String) : HomeUiAction
    data class OnShareJoinCodeClicked(val code: String) : HomeUiAction
    data class OnManageMembersClicked(val communityId: String) : HomeUiAction
    data class OnViewMembersClicked(val communityId: String) : HomeUiAction
    data class OnEditCommunityClicked(val community: Community) : HomeUiAction
    data class OnDeleteCommunityClicked(val communityId: String) : HomeUiAction
    data object OnCreateCommunityClicked : HomeUiAction
    data class OnJoinCommunityClicked(val code: String) : HomeUiAction
    data object OnProfileClicked : HomeUiAction
    data object OnUserLogout : HomeUiAction
    data class OnOptionsMenuClicked(val community: Community): HomeUiAction

}
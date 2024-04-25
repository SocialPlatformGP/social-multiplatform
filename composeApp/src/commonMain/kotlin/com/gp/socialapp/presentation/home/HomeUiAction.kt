package com.gp.socialapp.presentation.home

sealed interface HomeUiAction {
    data class OnCommunityClicked(val communityId: String) : HomeUiAction
    data class OnCommunityLogout(val id: String) : HomeUiAction

    data object OnCreateCommunityClicked : HomeUiAction
    data class OnJoinCommunityClicked(val code: String) : HomeUiAction
    data object OnProfileClicked : HomeUiAction
    data object OnUserLogout : HomeUiAction

}
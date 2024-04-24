package com.gp.socialapp.presentation.home

sealed interface HomeUiAction {
    data class OnCommunityClicked(val communityId: String) : HomeUiAction
    data object OnCreateCommunityClicked : HomeUiAction
    data object OnJoinCommunityClicked : HomeUiAction
    data object OnProfileClicked : HomeUiAction

}
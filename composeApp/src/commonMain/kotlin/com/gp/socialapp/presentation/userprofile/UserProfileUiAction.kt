package com.gp.socialapp.presentation.userprofile

sealed interface UserProfileUiAction {
    data object OnNavigateUpClicked : UserProfileUiAction
    data class OnSendMsgClicked(val userId: String) : UserProfileUiAction
}
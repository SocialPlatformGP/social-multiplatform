package com.gp.socialapp.presentation.home

sealed interface NavigationActions {
    data object OnUserLogout : NavigationActions
    data object OnBackToHome : NavigationActions
    data object OnOpenProfile : NavigationActions
    data object OnOpenSettings : NavigationActions
    data object OnOpenNotifications : NavigationActions
    data object OnOpenDrawer : NavigationActions
    data class OnChangeCommunity(val communityId: String) : NavigationActions

}
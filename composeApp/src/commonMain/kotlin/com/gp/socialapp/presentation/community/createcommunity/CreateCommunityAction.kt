package com.gp.socialapp.presentation.community.createcommunity

sealed class CreateCommunityAction {
    data object OnBackClicked : CreateCommunityAction()
}
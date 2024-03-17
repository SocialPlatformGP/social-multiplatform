package com.gp.socialapp.presentation.post.feed

import com.gp.socialapp.data.post.source.remote.model.Post

sealed class NavigationAction{
    object NavigateToProfile : NavigationAction()
    object NavigateToSearch : NavigationAction()
    object NavigateToPost : NavigationAction()
    object NavigateToSuggestPost : NavigationAction()
    data class NavigateToPostDetails(val post: Post) : NavigationAction()
}
package com.gp.socialapp.presentation.home.container

import com.gp.socialapp.data.auth.source.remote.model.User

data class HomeContainerUiState(
    val currentUser: User = User(),
    val isLoggedOut: Boolean = false,
    )

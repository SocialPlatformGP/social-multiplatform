package com.gp.socialapp.presentation.settings

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings

data class SettingsUiState(
    val currentUser: User = User(),
    val currentUserSettings: UserSettings = UserSettings(),
)

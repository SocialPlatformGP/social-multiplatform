package com.gp.socialapp.presentation.settings

import kotlinx.datetime.LocalDateTime

sealed interface SettingsAction {
    sealed interface AccountSettingsAction : SettingsAction {
        data class UpdateAvatar(val avatarByteArray: ByteArray) : AccountSettingsAction
        data class UpdateName(val name: String) : AccountSettingsAction
        data class UpdateBio(val bio: String) : AccountSettingsAction
        data class UpdatePhoneNumber(val phoneNumber: String) : AccountSettingsAction
        data object DeleteAccount : AccountSettingsAction
    }
    sealed interface DisplaySettingsAction : SettingsAction {
        data class UpdateFontSize(val fontSize: Float) : DisplaySettingsAction
        data class UpdateLanguage(val language: String) : DisplaySettingsAction
        data class UpdateTheme(val theme: String) : DisplaySettingsAction
    }
}
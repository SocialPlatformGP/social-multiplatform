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
    sealed interface SecuritySettingsAction : SettingsAction {
        data class UpdatePassword(val oldPassword: String, val newPassword: String) : SecuritySettingsAction
        data class UpdateEmail(val email: String) : SecuritySettingsAction
    }
    sealed interface PrivacySettingsAction : SettingsAction {
        data class UpdateAllowMessagesFrom(val allowMessagesFrom: String) : PrivacySettingsAction
        data class UpdateWhoCanAddToGroups(val whoCanAddToGroups: String) : PrivacySettingsAction
    }
    sealed interface NotificationSettingsAction : SettingsAction {
        data class UpdateAllowNotifications(val value: Boolean) : NotificationSettingsAction
        data class UpdateAllowChatNotifications(val value: Boolean) : NotificationSettingsAction
        data class UpdateAllowPostNotifications(val value: Boolean) : NotificationSettingsAction
        data class UpdateAllowAssignmentsNotifications(val value: Boolean) : NotificationSettingsAction
        data class UpdateAllowCalendarNotifications(val value: Boolean) : NotificationSettingsAction
    }
}
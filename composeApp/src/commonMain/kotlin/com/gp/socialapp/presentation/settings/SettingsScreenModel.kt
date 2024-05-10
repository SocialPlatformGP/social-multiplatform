package com.gp.socialapp.presentation.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.UserData
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsScreenModel(
    private val authRepo: AuthenticationRepository,
    private val userRepo: UserRepository
): ScreenModel {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val state = _uiState.asStateFlow()
    fun init() {
        getUserSettings()
        getSignedInUser()
    }

    private fun getUserSettings() {
        screenModelScope.launch {
            val result = userRepo.getUserSettings()
                when(result){
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(currentUserSettings = result.data)
                        }
                    }
                    is Result.Error -> {
                        Napier.d("Error getting user settings: ${result.message}")
                    }
                    else -> Unit
                }
        }
    }

    private fun getSignedInUser() {
        screenModelScope.launch {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Error -> {
                        /*TODO: Handle Error*/
                    }
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(currentUser = result.data)
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        _uiState.value = SettingsUiState()
    }

    fun onAction(action: SettingsAction) {
        when(action){
            SettingsAction.AccountSettingsAction.DeleteAccount -> {
                deleteAccount()
            }
            is SettingsAction.AccountSettingsAction.UpdateBio -> {
                updateBio(action.bio)
            }
            is SettingsAction.AccountSettingsAction.UpdateName -> {
                updateName(action.name)
            }
            is SettingsAction.AccountSettingsAction.UpdatePhoneNumber -> {
                updatePhoneNumber(action.phoneNumber)
            }
            is SettingsAction.AccountSettingsAction.UpdateAvatar -> {
                updateAvatar(action.avatarByteArray)
            }
            is SettingsAction.DisplaySettingsAction.UpdateFontSize -> {
                updateFontSize(action.fontSize)
            }
            is SettingsAction.DisplaySettingsAction.UpdateLanguage -> {
                updateLanguage(action.language)
            }
            is SettingsAction.DisplaySettingsAction.UpdateTheme -> {
                updateTheme(action.theme)
            }
            is SettingsAction.NotificationSettingsAction.UpdateAllowAssignmentsNotifications -> {
                updateAllowAssignmentsNotifications(action.value)
            }
            is SettingsAction.NotificationSettingsAction.UpdateAllowCalendarNotifications -> {
                updateAllowCalendarNotifications(action.value)
            }
            is SettingsAction.NotificationSettingsAction.UpdateAllowChatNotifications -> {
                updateAllowChatNotifications(action.value)
            }
            is SettingsAction.NotificationSettingsAction.UpdateAllowNotifications -> {
                updateAllowNotifications(action.value)
            }
            is SettingsAction.NotificationSettingsAction.UpdateAllowPostNotifications -> {
                updateAllowPostNotifications(action.value)
            }
            is SettingsAction.PrivacySettingsAction.UpdateAllowMessagesFrom -> {
                updateAllowMessagesFrom(action.allowMessagesFrom)
            }
            is SettingsAction.PrivacySettingsAction.UpdateWhoCanAddToGroups -> {
                updateWhoCanAddToGroups(action.whoCanAddToGroups)
            }
            is SettingsAction.SecuritySettingsAction.UpdateEmail -> {
                updateEmail(action.email)
            }
            is SettingsAction.SecuritySettingsAction.UpdatePassword -> {
                updatePassword(action.oldPassword, action.newPassword)
            }
        }
    }

    private fun updatePassword(oldPassword: String, newPassword: String) {
        screenModelScope.launch (DispatcherIO) {
            val result = userRepo.changePassword(oldPassword, newPassword)
                when(result){
                    is Result.Success -> {
                        println("Password changed successfully")
                        getSignedInUser()
                        Napier.d("Password changed successfully")
                    }
                    is Result.Error -> {
                        println("Error changing password: ${result.message}")
                        Napier.d("Error changing password: ${result.message}")
                    }
                    else -> Unit
                }
        }
    }

    private fun updateEmail(email: String) {
        screenModelScope.launch(DispatcherIO){
            userRepo.changeEmail(_uiState.value.currentUser.id, email).onSuccess {
                println("Email changed successfully")
                getSignedInUser()
                Napier.d("Email changed successfully")
            }.onFailure {
                println("Error changing email: $it")
                Napier.d("Error changing email: $it")
            }
        }
    }
    private fun updateStringRemoteUserSetting(tag: String, value: String){
        screenModelScope.launch(DispatcherIO){
            userRepo.updateStringRemoteUserSetting(_uiState.value.currentUser.id, tag, value).onSuccess {
                println("User setting updated successfully")
                getSignedInUser()
                getUserSettings()
                Napier.d("User setting updated successfully")
            }.onFailure {
                println("Error updating user setting: $it")
                Napier.d("Error updating user setting: $it")
            }
        }
    }
    private fun updateBooleanRemoteUserSetting(tag: String, value: Boolean){
        screenModelScope.launch(DispatcherIO){
            userRepo.updateBooleanRemoteUserSetting(_uiState.value.currentUser.id, tag, value).onSuccess {
                println("User setting updated successfully")
                getUserSettings()
                Napier.d("User setting updated successfully")
            }.onFailure {
                println("Error updating user setting: $it")
                Napier.d("Error updating user setting: $it")
            }
        }
    }

    private fun updateWhoCanAddToGroups(whoCanAddToGroups: String) {
        updateStringRemoteUserSetting(UserData.WHO_CAN_ADD_TO_GROUPS.value, whoCanAddToGroups)
    }

    private fun updateAllowMessagesFrom(allowMessagesFrom: String) {
        updateStringRemoteUserSetting(UserData.ALLOW_MESSAGES_FROM.value, allowMessagesFrom)
    }

    private fun updateAllowPostNotifications(value: Boolean) {
        updateBooleanRemoteUserSetting(UserData.ALLOW_POST_NOTIFICATIONS.value, value)
    }

    private fun updateAllowNotifications(value: Boolean) {
        updateBooleanRemoteUserSetting(UserData.ALLOW_NOTIFICATIONS.value, value)
    }

    private fun updateAllowChatNotifications(value: Boolean) {
        updateBooleanRemoteUserSetting(UserData.ALLOW_CHAT_NOTIFICATIONS.value, value)
    }

    private fun updateAllowCalendarNotifications(value: Boolean) {
        updateBooleanRemoteUserSetting(UserData.ALLOW_CALENDAR_NOTIFICATIONS.value, value)
    }

    private fun updateAllowAssignmentsNotifications(value: Boolean) {
        updateBooleanRemoteUserSetting(UserData.ALLOW_ASSIGNMENTS_NOTIFICATIONS.value, value)
    }

    private fun updateTheme(theme: String) {
        screenModelScope.launch {
            userRepo.setTheme(theme)
            getUserSettings()
        }
    }

    private fun updateLanguage(language: String) {
        //TODO("Not yet implemented")
    }

    private fun updateFontSize(fontSize: Float) {
        //TODO("Not yet implemented")
    }

    private fun updateAvatar(avatarByteArray: ByteArray) {
        screenModelScope.launch (DispatcherIO) {
            userRepo.updateUserAvatar(avatarByteArray, _uiState.value.currentUser.id).onSuccess {
                getSignedInUser()
                println("Avatar updated successfully")
                Napier.d("Avatar updated successfully")
            }.onFailure {
                println("Error updating avatar: $it")
                Napier.d("Error updating avatar: $it")
            }
        }
    }

    private fun updatePhoneNumber(phoneNumber: String) {
        screenModelScope.launch (DispatcherIO) {
            userRepo.updatePhoneNumber(_uiState.value.currentUser.id, phoneNumber).onSuccess {
                getSignedInUser()
                println("Phone number updated successfully")
                Napier.d("Phone number updated successfully")
            }.onFailure {
                println("Error updating phone number: $it")
                Napier.d("Error updating phone number: $it")
            }
        }
    }

    private fun updateName(name: String) {
        screenModelScope.launch (DispatcherIO) {
            userRepo.updateName(_uiState.value.currentUser.id, name).onSuccess {
                getSignedInUser()
                println("Name updated successfully")
                Napier.d("Name updated successfully")
            }.onFailure {
                println("Error updating name: $it")
                Napier.d("Error updating name: $it")
            }
        }
    }

    private fun updateBio(bio: String) {
        screenModelScope.launch (DispatcherIO) {
            userRepo.updateStringRemoteUserSetting(_uiState.value.currentUser.id, UserData.BIO.value, bio).onSuccess {
                getSignedInUser()
                println("Bio updated successfully")
                Napier.d("Bio updated successfully")
            }.onFailure {
                println("Error updating bio: $it")
                Napier.d("Error updating bio: $it")
            }
        }
    }

    private fun deleteAccount() {
        screenModelScope.launch (DispatcherIO) {
            authRepo.deleteAccount(_uiState.value.currentUser.id).onSuccess {
                println("Account deleted successfully")
                _uiState.update { it.copy(isUserDeleted = true) }
                Napier.d("Account deleted successfully")
            }.onFailure {
                println("Error deleting account: $it")
                Napier.d("Error deleting account: $it")
            }
        }
    }
}
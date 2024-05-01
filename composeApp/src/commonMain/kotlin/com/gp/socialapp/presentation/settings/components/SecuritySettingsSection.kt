package com.gp.socialapp.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.presentation.auth.util.Validator
import com.gp.socialapp.presentation.settings.SettingsAction

@Composable
fun SecuritySettingsSection(
    modifier: Modifier = Modifier,
    currentUser: User,
    currentUserSettings: UserSettings,
    onAction: (SettingsAction) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        SettingsGroup(
            title = "Sign-in & Security",
        ) {
            TextSettingItem(icon = Icons.Default.Email,
                title = "Email",
                value = currentUser.email,
                onSave = { value ->
                    onAction(SettingsAction.SecuritySettingsAction.UpdateEmail(value))
                },
                onCheck = {
                    Validator.EmailValidator.validateAll(it)
                })
            PasswordSettingItem(onCheckOldPassword = {
                Validator.PasswordValidator.validateAll(it)
            }, onCheckNewPassword = {
                Validator.PasswordValidator.validateAll(it)
            }, onSave = { oldPassword, newPassword ->
                onAction(SettingsAction.SecuritySettingsAction.UpdatePassword(oldPassword, newPassword))
            })

        }
    }
}
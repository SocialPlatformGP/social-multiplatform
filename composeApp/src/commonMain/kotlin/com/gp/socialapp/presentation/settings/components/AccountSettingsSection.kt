package com.gp.socialapp.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.auth.util.Validator
import com.gp.socialapp.presentation.chat.creategroup.components.ModifiableAvatarSection
import com.gp.socialapp.presentation.settings.SettingsAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettingsSection(
    modifier: Modifier = Modifier,
    currentUser: User,
    onAction: (SettingsAction.AccountSettingsAction) -> Unit,
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        SettingsGroup(
            title = "Account Information"
        ) {
            ModifiableAvatarSection(
                avatarURL = currentUser.profilePictureURL,
                isModifiable = true,
                onImagePicked = { newAvatar ->
                    onAction(SettingsAction.AccountSettingsAction.UpdateAvatar(newAvatar))
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            TextSettingItem(title = "Name", value = currentUser.name, onSave = { newName ->
                onAction(SettingsAction.AccountSettingsAction.UpdateName(newName))
            }, onCheck = { newName ->
                Validator.NameValidator.validateAll(newName)
            })
            TextSettingItem(title = "Phone Number",
                value = currentUser.phoneNumber,
                onSave = { newPhoneNumber ->
                    onAction(
                        SettingsAction.AccountSettingsAction.UpdatePhoneNumber(
                            newPhoneNumber
                        )
                    )
                },
                onCheck = { newPhoneNumber ->
                    Validator.PhoneNumberValidator.validateAll(newPhoneNumber)
                })
            TextSettingItem(title = "Bio", value = currentUser.bio, onSave = { newBio ->
                onAction(SettingsAction.AccountSettingsAction.UpdateBio(newBio))
            }, onCheck = { true })
        }
        ClickableSettingItem(
            needConfirmation = true,
            confirmationTitle = "Are you sure you want to delete your account?",
            confirmationMessage = "This action cannot be undone, all associated data with your account will be lost.",
            title = "Delete Account",
        ) {
            onAction(SettingsAction.AccountSettingsAction.DeleteAccount)
        }

    }
}
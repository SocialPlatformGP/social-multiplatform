package com.gp.socialapp.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.presentation.auth.util.Validator
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar
import com.gp.socialapp.presentation.chat.creategroup.components.ModifiableAvatarSection
import com.gp.socialapp.presentation.settings.components.ClickableSettingItem
import com.gp.socialapp.presentation.settings.components.SettingsGroup
import com.gp.socialapp.presentation.settings.components.TextSettingItem

object AccountSettingsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<SettingsScreenModel>()
        val state by screenModel.state.collectAsState()
        AccountSettingsContent(
            currentUser = state.currentUser,
            onAction = screenModel::onAction,
            onBackPressed = navigator::pop
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AccountSettingsContent(
        modifier: Modifier = Modifier,
        currentUser: User,
        onAction: (SettingsAction.AccountSettingsAction) -> Unit,
        onBackPressed: () -> Unit,
    ) {
        Scaffold(modifier = modifier, topBar = {
            TopAppBar(title = {
                Text(
                    text = "Account Settings", style = MaterialTheme.typography.titleMedium
                )
            }, navigationIcon = {
                IconButton(
                    onClick = onBackPressed
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back"
                    )
                }
            })
            HorizontalDivider()
        }) {
            Column(
                modifier = Modifier.padding(it).padding(16.dp).verticalScroll(rememberScrollState())
            ) {
                SettingsGroup(
                    title = "Account Information"
                ) {
                    ModifiableAvatarSection(
                        avatarURL = currentUser.profilePictureURL,
                        isModifiable = true,
                        onImagePicked = { newAvatar ->
                            onAction(SettingsAction.AccountSettingsAction.UpdateAvatar(newAvatar))
                        }
                    )
                    Spacer(Modifier.size(8.dp))
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
    }
}
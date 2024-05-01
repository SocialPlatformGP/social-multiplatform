package com.gp.socialapp.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Public
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.presentation.settings.SettingsAction

@Composable
fun NotificationsSettingsSection(
    modifier: Modifier = Modifier,
    currentUser: User,
    currentUserSettings: UserSettings,
    onAction: (SettingsAction) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        SettingsGroup(
            title = "Notifications",
        ) {
            SwitchSettingItem(
                name = "Allow notifications",
                icon = Icons.Default.NotificationsActive,
                isChecked = currentUserSettings.isNotificationsAllowed,
                onClick = {
                    onAction(
                        SettingsAction.NotificationSettingsAction.UpdateAllowNotifications(it)
                    )
                }
            )
            if(currentUserSettings.isNotificationsAllowed) {
                SwitchSettingItem(
                    name = "Chat Notifications",
                    icon = Icons.AutoMirrored.Filled.Chat,
                    isChecked = currentUserSettings.isChatNotificationsAllowed,
                    onClick = {
                        onAction(
                            SettingsAction.NotificationSettingsAction.UpdateAllowChatNotifications(it)
                        )
                    }
                )
                SwitchSettingItem(
                    name = "Post Notifications",
                    icon = Icons.Rounded.Public,
                    isChecked = currentUserSettings.isPostNotificationsAllowed,
                    onClick = {
                        onAction(
                            SettingsAction.NotificationSettingsAction.UpdateAllowPostNotifications(it)
                        )
                    }
                )
                SwitchSettingItem(
                    name = "Assignments Notifications",
                    icon = Icons.AutoMirrored.Filled.Assignment,
                    isChecked = currentUserSettings.isAssignmentsNotificationsAllowed,
                    onClick = {
                        onAction(
                            SettingsAction.NotificationSettingsAction.UpdateAllowAssignmentsNotifications(it)
                        )
                    }
                )
                SwitchSettingItem(
                    name = "Calendar Notifications",
                    icon = Icons.Rounded.CalendarMonth,
                    isChecked = currentUserSettings.isCalendarNotificationsAllowed,
                    onClick = {
                        onAction(
                            SettingsAction.NotificationSettingsAction.UpdateAllowCalendarNotifications(it)
                        )
                    }
                )
            }
        }
    }
}
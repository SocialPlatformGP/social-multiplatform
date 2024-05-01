package com.gp.socialapp.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.PrivacyOptions
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.presentation.settings.SettingsAction

@Composable
fun PrivacySettingsSection(
    modifier: Modifier = Modifier,
    currentUser: User,
    currentUserSettings: UserSettings,
    onAction: (SettingsAction) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        SettingsGroup(
            title = "Privacy & Safety",
        ) {
            ListSettingItem(title = "Allow messages from",
                value = currentUserSettings.allowMessagesFrom,
                items = PrivacyOptions.entries.map { it.value },
                onSave = {
                    onAction(
                        SettingsAction.PrivacySettingsAction.UpdateAllowMessagesFrom(it)
                    )
                })
            ListSettingItem(title = "Who can add you to groups",
                value = currentUserSettings.whoCanAddToGroups,
                items = PrivacyOptions.entries.map { it.value },
                onSave = {
                    onAction(
                        SettingsAction.PrivacySettingsAction.UpdateWhoCanAddToGroups(it)
                    )
                })
        }
    }
}
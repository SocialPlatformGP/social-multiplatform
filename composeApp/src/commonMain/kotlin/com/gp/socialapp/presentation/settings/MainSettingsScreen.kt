package com.gp.socialapp.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SettingsAccessibility
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.settings.components.AccountSettingsSection
import com.gp.socialapp.presentation.settings.components.DisplaySettingsContent
import com.gp.socialapp.presentation.settings.components.NotificationsSettingsSection
import com.gp.socialapp.presentation.settings.components.PrivacySettingsSection
import com.gp.socialapp.presentation.settings.components.SecuritySettingsSection

object MainSettingsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<SettingsScreenModel>()
        val state by screenModel.state.collectAsState()
        if(state.isUserDeleted) {
            navigator.replaceAll(LoginScreen)
        } else {
            MainSettingsContent(onBackPressed = { navigator.pop() },
                currentUser = state.currentUser,
                currentUserSettings = state.currentUserSettings,
                onAction = { action -> screenModel.onAction(action) })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainSettingsContent(
        modifier: Modifier = Modifier,
        currentUser: User,
        currentUserSettings: UserSettings,
        onAction: (SettingsAction) -> Unit,
        onBackPressed: () -> Unit
    ) {
        Scaffold(modifier = modifier, topBar = {
            TopAppBar(title = {
                Text(
                    text = "Settings", style = MaterialTheme.typography.titleMedium
                )
            }, navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back"
                    )
                }
            })
            HorizontalDivider()
        }) {
            Column(
                modifier = Modifier.padding(it).padding(16.dp).verticalScroll(rememberScrollState())
            ) {
                AccountSettingsSection(
                    currentUser = currentUser, onAction = onAction
                )
                SecuritySettingsSection(
                    currentUser = currentUser,
                    currentUserSettings = currentUserSettings,
                    onAction = onAction
                )
                PrivacySettingsSection(
                    currentUser = currentUser,
                    currentUserSettings = currentUserSettings,
                    onAction = onAction
                )
                NotificationsSettingsSection(
                    currentUser = currentUser,
                    currentUserSettings = currentUserSettings,
                    onAction = onAction
                )
                DisplaySettingsContent(
                    currentUserSettings = currentUserSettings, onAction = onAction
                )
            }
        }
    }
}
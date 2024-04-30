package com.gp.socialapp.presentation.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.settings.components.SettingsTabItem

object MainSettingsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<SettingsScreenModel>()
        val state by screenModel.state.collectAsState()
        MainSettingsContent(
            onBackPressed = { navigator.pop() },
            onSettingsTabClick = { tab ->
                when (tab) {
                    SettingsTab.ACCOUNT -> navigator.push(AccountSettingsScreen)
                    SettingsTab.SIGN_IN_SECURITY -> navigator.push(SecuritySettingsScreen)
                    SettingsTab.PRIVACY_SAFETY -> navigator.push(PrivacySettingsScreen)
                    SettingsTab.NOTIFICATIONS -> navigator.push(NotificationsSettingsScreen)
                    SettingsTab.ACCESSIBILITY_DISPLAY_LANGUAGES -> navigator.push(DisplaySettingsScreen)
                    SettingsTab.ADDITIONAL_RESOURCES -> navigator.push(AdditionalResourcesScreen)
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainSettingsContent(
        modifier: Modifier = Modifier,
        onSettingsTabClick: (SettingsTab) -> Unit,
        onBackPressed: () -> Unit
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }){
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
                HorizontalDivider()
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(16.dp)
            ) {
                items(SettingsTab.entries) { tab ->
                    SettingsTabItem(
                        title = tab.title,
                        description = tab.description,
                        icon = tab.icon,
                        onClick = { onSettingsTabClick(tab) }
                    )
                }
            }
        }
    }
}
enum class SettingsTab(val title: String, val icon: ImageVector, val description: String,) {
    ACCOUNT("Your account", Icons.Default.ManageAccounts, "See and manage information about your account."),
    SIGN_IN_SECURITY("Sign-in & security", Icons.Default.Lock, "Manage your sign-in & security settings."),
    PRIVACY_SAFETY("Privacy & safety", Icons.Default.Security, "Manage you privacy and safety settings."),
    NOTIFICATIONS("Notifications", Icons.Default.NotificationsActive, "Manage your notification settings."),
    ACCESSIBILITY_DISPLAY_LANGUAGES("Display and languages", Icons.Default.SettingsAccessibility, "Manage how EduLink content is displayed to you."),
    ADDITIONAL_RESOURCES("Additional Resources", Icons.Default.MoreHoriz, "See other resources about EduLink.")
}
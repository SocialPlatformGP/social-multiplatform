package com.gp.socialapp.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.presentation.auth.util.Validator
import com.gp.socialapp.presentation.chat.creategroup.components.ModifiableAvatarSection
import com.gp.socialapp.presentation.settings.components.ClickableSettingItem
import com.gp.socialapp.presentation.settings.components.ListSettingItem
import com.gp.socialapp.presentation.settings.components.SettingsGroup
import com.gp.socialapp.presentation.settings.components.SliderSettingItem
import com.gp.socialapp.presentation.settings.components.TextSettingItem
import com.gp.socialapp.theme.LocalThemeIsDark

object DisplaySettingsScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<SettingsScreenModel>()
        val state by screenModel.state.collectAsState()
        DisplaySettingsContent(
            currentUser = state.currentUser,
            currentUserSettings = state.currentUserSettings,
            onAction = { action -> screenModel.onAction(action) },
            onBackPressed = { navigator.pop() }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DisplaySettingsContent(
        modifier: Modifier = Modifier,
        currentUser: User,
        currentUserSettings: UserSettings,
        onAction: (SettingsAction.DisplaySettingsAction) -> Unit,
        onBackPressed: () -> Unit,
    ) {
        Scaffold(modifier = modifier, topBar = {
            TopAppBar(title = {
                Text(
                    text = "Display Settings", style = MaterialTheme.typography.titleMedium
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
                var isDark by LocalThemeIsDark.current
                SettingsGroup(
                    title = "Display Preferences",
                ) {
                    ListSettingItem(
                        title = "Theme",
                        icon = Icons.Default.Brightness4,
                        value = if(isDark) "Dark" else "Light",
                        items = listOf("Light", "Dark",),
                        onSave = { value ->
//                            onAction(SettingsAction.DisplaySettingsAction.UpdateTheme(value)) }
                            isDark = value == "Dark"
                        }
                    )
                    ListSettingItem(
                        title = "Display Language",
                        icon = Icons.Default.Translate,
                        value = "English",
                        items = listOf("English", "Arabic"),
                        onSave = { value -> onAction(SettingsAction.DisplaySettingsAction.UpdateLanguage(value)) }
                    )
                    SliderSettingItem(
                        icon = Icons.Default.Title,
                        title = "Font Size",
                        initialValue = 3f,
                        valueRange = 1f..5f,
                        steps = 5,
                        onValueChange = { value -> onAction(SettingsAction.DisplaySettingsAction.UpdateFontSize(value)) },
                        description = "Adjust the font size of the app",
                    )
                }
            }
        }
    }
}
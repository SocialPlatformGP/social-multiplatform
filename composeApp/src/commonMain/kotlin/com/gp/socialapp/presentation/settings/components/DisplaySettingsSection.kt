package com.gp.socialapp.presentation.settings.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.presentation.settings.SettingsAction
import com.gp.socialapp.theme.LocalThemeIsDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySettingsContent(
    modifier: Modifier = Modifier,
    currentUserSettings: UserSettings,
    onAction: (SettingsAction.DisplaySettingsAction) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        var isDark by LocalThemeIsDark.current
        val isSystemInDarkTheme = isSystemInDarkTheme()
        SettingsGroup(
            title = "Display Preferences",
        ) {
            ListSettingItem(
                title = "Theme",
                icon = Icons.Default.Brightness4,
                value = currentUserSettings.theme,
                items = AppThemeOptions.entries.map { entry -> entry.value },
                onSave = { value ->
                    onAction(SettingsAction.DisplaySettingsAction.UpdateTheme(value))
                    isDark =
                        value == AppThemeOptions.DARK.value || (value == AppThemeOptions.SYSTEM_DEFAULT.value && isSystemInDarkTheme)
                }
            )
            ListSettingItem(
                title = "Display Language",
                icon = Icons.Default.Translate,
                value = "English",
                items = listOf("English", "Arabic"),
                onSave = { value ->
                    onAction(
                        SettingsAction.DisplaySettingsAction.UpdateLanguage(
                            value
                        )
                    )
                }
            )
            SliderSettingItem(
                icon = Icons.Default.Title,
                title = "Font Size",
                initialValue = 3f,
                valueRange = 1f..5f,
                steps = 5,
                onValueChange = { value ->
                    onAction(
                        SettingsAction.DisplaySettingsAction.UpdateFontSize(
                            value
                        )
                    )
                },
                description = "Adjust the font size of the app",
            )
        }
    }
}

enum class AppThemeOptions(val value: String) {
    LIGHT("Light"),
    DARK("Dark"),
    SYSTEM_DEFAULT("System Default")
}
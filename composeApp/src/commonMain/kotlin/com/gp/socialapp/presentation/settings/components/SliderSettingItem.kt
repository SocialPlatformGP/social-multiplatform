package com.gp.socialapp.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SliderSettingItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    initialValue: Float,
    onValueChange: (Float) -> Unit,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    description: String,
) {
    var sliderValue by remember { mutableStateOf(initialValue) }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Transparent,
    ) {
        Column {
            ListItem(
                modifier = Modifier.fillMaxWidth().then(modifier),
                headlineContent = { Text(title) },
                supportingContent = {
                    Column {
                        Text(description)
                        Slider(
                            value = sliderValue,
                            onValueChange = { value ->
                                onValueChange(value)
                                sliderValue = value
                            },
                            modifier = Modifier.padding(end = 16.dp),
                            enabled = enabled,
                            valueRange = valueRange,
                            steps = steps,
                        )
                    }
                },
                leadingContent = { Icon(icon, null) },
            )
            HorizontalDivider()
        }
    }
}
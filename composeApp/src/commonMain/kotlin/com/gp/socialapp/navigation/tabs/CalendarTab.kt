package com.gp.socialapp.navigation.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object CalendarTab: Tab {
    override val options: TabOptions
    @Composable
    get() {
        val title = "Calendar"
        val icon = rememberVectorPainter(Icons.Rounded.CalendarMonth)

        return remember {
            TabOptions(
                index = 3u,
                title = title,
                icon = icon
            )
        }
    }

    @Composable
    override fun Content() {
        //TODO
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                "Calendar Tab Content",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
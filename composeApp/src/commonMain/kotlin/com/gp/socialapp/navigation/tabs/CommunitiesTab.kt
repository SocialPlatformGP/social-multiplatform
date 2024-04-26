package com.gp.socialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.gp.socialapp.presentation.chat.home.ChatHomeScreen
import com.gp.socialapp.presentation.home.HomeScreen

object CommunitiesTab: Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Communities"
            val icon = rememberVectorPainter(Icons.Default.Diversity3)

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
        Navigator(screen = HomeScreen)
    }
}
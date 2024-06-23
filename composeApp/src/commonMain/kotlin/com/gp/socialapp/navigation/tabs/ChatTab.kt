package com.gp.socialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.gp.socialapp.presentation.chat.home.ChatHomeScreen
import kotlin.jvm.Transient

data class ChatTab(
    @Transient
    private val onNavigation: (Boolean) -> Unit,
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Chat"
            val icon = rememberVectorPainter(Icons.AutoMirrored.Filled.Chat)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = ChatHomeScreen()) { navigator ->
            LaunchedEffect(navigator.lastItem) {
                onNavigation(navigator.lastItem is ChatHomeScreen)
            }
            SlideTransition(navigator = navigator)
        }
    }
}
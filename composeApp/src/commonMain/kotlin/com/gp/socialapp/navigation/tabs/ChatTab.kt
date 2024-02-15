package com.gp.socialapp.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.gp.socialapp.presentation.chat.home.ChatHomeScreen

object ChatTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Chat"
            val icon = rememberVectorPainter(Icons.AutoMirrored.Rounded.Chat)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = ChatHomeScreen)
    }
}
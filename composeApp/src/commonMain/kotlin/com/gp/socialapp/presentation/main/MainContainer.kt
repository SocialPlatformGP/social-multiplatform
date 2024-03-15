package com.gp.socialapp.presentation.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.gp.socialapp.navigation.tabs.ChatTab
import com.gp.socialapp.navigation.tabs.MaterialTab
import com.gp.socialapp.tabs.PostsTab


object MainContainer : Screen {

    @Composable
    override fun Content() {
        TabNavigator(PostsTab) {
            Scaffold(
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    NavigationBar {
                        TabNavigationItem(tab = PostsTab)
                        TabNavigationItem(tab = ChatTab)
                        TabNavigationItem(tab = MaterialTab)
                    }
                }
            )
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        NavigationBarItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = {
                tab.options.icon?.let { icon ->
                    Icon(
                        painter = icon,
                        contentDescription =
                        tab.options.title
                    )
                }
            }
        )
    }

}
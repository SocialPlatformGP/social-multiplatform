package com.gp.socialapp.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.navigation.tabs.ChatTab
import com.gp.socialapp.navigation.tabs.MaterialTab
import com.gp.socialapp.presentation.main.userinfo.UserInformationScreen
import com.gp.socialapp.tabs.PostsTab


data class MainContainer(val signedInUser: User) : Screen {

    @Composable
    override fun Content() {
        val navigtor = LocalNavigator.currentOrThrow
        if(signedInUser.isDataComplete) {
            TabNavigator(PostsTab) {
                Scaffold(
                    content = {
                        Column(
                            modifier = Modifier.padding(it)
                        ) {
                            CurrentTab()
                        }
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
        } else {
            navigtor.replace(UserInformationScreen(signedInUser))
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
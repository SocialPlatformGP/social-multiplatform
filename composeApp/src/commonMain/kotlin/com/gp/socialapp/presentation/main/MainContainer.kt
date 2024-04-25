package com.gp.socialapp.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.launch


data class MainContainer(val signedInUser: User) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        if(signedInUser.isDataComplete) {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        Text("EduLink", modifier = Modifier.padding(16.dp))
                        HorizontalDivider()
                        NavigationDrawerItem(
                            label = { Text(text = "Item 1") },
                            selected = true,
                            onClick = { /*TODO*/ }
                        )
                        NavigationDrawerItem(
                            label = { Text(text = "Item 2") },
                            selected = false,
                            onClick = { /*TODO*/ }
                        )
                        NavigationDrawerItem(
                            label = { Text(text = "Item 3") },
                            selected = false,
                            onClick = { /*TODO*/ }
                        )
                    }
                },
                drawerState = drawerState,
            ) {
                TabNavigator(PostsTab) {
                    Scaffold(
                        content = {
                            Column(
                                modifier = Modifier.padding(it)
                            ) {
                                CurrentTab()
                            }
                        },
                        topBar = {
                            MainTopBar(
                                onSearchClicked = { /*TODO*/ },
                                onNotificationClicked = { /*TODO*/ },
                                onNavDrawerIconClicked = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                            )
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
        } else {
            navigator.replace(UserInformationScreen(signedInUser))
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
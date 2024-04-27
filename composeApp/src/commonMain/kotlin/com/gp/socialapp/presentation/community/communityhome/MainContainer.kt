package com.gp.socialapp.presentation.community.communityhome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.gp.socialapp.navigation.tabs.CommunityMembersTab
import com.gp.socialapp.navigation.tabs.MaterialTab
import com.gp.socialapp.navigation.tabs.PostsTab
import com.gp.socialapp.navigation.util.BottomTabNavigationItem
import com.gp.socialapp.presentation.home.HomeUiAction
import com.gp.socialapp.presentation.home.components.HomeTopBar
import kotlinx.coroutines.launch


data class CommunityHomeContainer(val communityId: String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var isBarsVisible by remember { mutableStateOf(true) }
        val onNavigation: (Boolean) -> Unit = { isBarsVisible = it }
        val onAction: (HomeUiAction) -> Unit = {
            when (it) {
                HomeUiAction.OnUserLogout -> {
                    { /*TODO*/ }
                }

                HomeUiAction.OnOpenDrawer -> {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }

                else -> Unit
            }
        }
//        ModalNavigationDrawer(
//            drawerContent = {
//                ModalDrawerSheet {
//                    Text("EduLink", modifier = Modifier.padding(16.dp))
//                    HorizontalDivider()
//                    NavigationDrawerItem(label = { Text(text = "Item 1") },
//                        selected = true,
//                        onClick = { /*TODO*/ })
//                    NavigationDrawerItem(label = { Text(text = "Item 2") },
//                        selected = false,
//                        onClick = { /*TODO*/ })
//                    NavigationDrawerItem(label = { Text(text = "Item 3") },
//                        selected = false,
//                        onClick = { /*TODO*/ })
//                }
//            },
//            drawerState = drawerState,
//        ) {
        TabNavigator(PostsTab(communityId, onNavigation)) {
            Scaffold(content = {
                Column(
                    modifier = Modifier.padding(it)
                ) {
                    CurrentTab()
                }
            }, topBar = {
                if (isBarsVisible) HomeTopBar(
                    action = onAction
                )
            },
                bottomBar = {
                    if (isBarsVisible) {
                        NavigationBar {
                            BottomTabNavigationItem(tab = MaterialTab(communityId))
                            BottomTabNavigationItem(tab = PostsTab(communityId, onNavigation))
                            BottomTabNavigationItem(tab = CommunityMembersTab(communityId))
                        }
                    }
                })
        }
    }
}

//}
package com.gp.socialapp.presentation.community.communityhome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.navigation.tabs.CommunityMembersTab
import com.gp.socialapp.navigation.tabs.MaterialTab
import com.gp.socialapp.navigation.tabs.PostsTab
import com.gp.socialapp.navigation.util.BottomTabNavigationItem
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.home.HomeContainer
import com.gp.socialapp.presentation.home.HomeUiAction
import com.gp.socialapp.presentation.post.search.SearchScreen
import com.seiko.imageloader.ui.AutoSizeImage
import kotlinx.coroutines.launch


data class CommunityHomeContainer(
    val communities: List<Community>,
    val user: User,
    val onAction: (HomeUiAction) -> Unit,
    val communityId: String,
    val startingTab: CommunityHomeTab = CommunityHomeTab.POSTS
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var communityId by remember { mutableStateOf(communityId) }
        var isBarsVisible by remember { mutableStateOf(true) }
        val onNavigation: (Boolean) -> Unit = { isBarsVisible = it }
        val onAction: (HomeUiAction) -> Unit = {
            when (it) {
                HomeUiAction.OnUserLogout -> {
                    navigator.replaceAll(LoginScreen)
                }

                HomeUiAction.OnOpenDrawer -> {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }

                is HomeUiAction.OnCommunityClicked -> {
                    isBarsVisible = false


                }

                else -> {
                    onAction(it)
                }
            }
        }
        val defaultTab = when (startingTab) {
            CommunityHomeTab.POSTS -> PostsTab(communityId, onNavigation)
            CommunityHomeTab.MATERIALS -> MaterialTab(communityId)
            CommunityHomeTab.MEMBERS -> CommunityMembersTab(communityId)
        }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.padding(8.dp).fillMaxSize()
                    ) {
                        if (user.profilePictureURL.isNotBlank())

                            AutoSizeImage(
                                url = user.profilePictureURL,
                                contentDescription = "user image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.padding(top = 16.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .size(64.dp).clip(CircleShape)
                            )
                        else
                            Box(
                                modifier = Modifier.padding(top = 16.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .size(64.dp).clip(CircleShape)
                                    .background(Color.Red)
                            ) {
                                Text(
                                    text = if (user.firstName.isNotBlank()) user.firstName[0].toString()
                                        .uppercase() + user.lastName[0].toString()
                                        .uppercase() else "Unknown",
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            user.firstName + " " + user.lastName,
                            fontSize = 24.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            user.email,
                            fontSize = 12.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        NavigationDrawerItem(
                            label = { Text(text = "Home") },
                            selected = false,
                            onClick = {
                                navigator.replaceAll(
                                    HomeContainer()
                                )
                            }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        NavigationDrawerItem(
                            label = {
                                Text(text = communities.filter { it.id == communityId }
                                    .first().name)
                            },
                            selected = true,
                            onClick = {},
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            OutlinedButton(
                                onClick = {
                                    onAction(HomeUiAction.OnUserLogout)
                                    navigator.replaceAll(LoginScreen)
                                },
                                modifier = Modifier.weight(1f).padding(8.dp)
                            ) {
                                Text(
                                    text = "Logout",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }

                            Button(
                                onClick = {

                                },
                                modifier = Modifier.weight(1f).padding(8.dp)

                            ) {
                                Text(
                                    text = "Settings",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            })

        {
            TabNavigator(defaultTab) { tabNavigator ->
                Scaffold(content = { paddingValues ->
                    Column(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        CurrentTab()
                    }
                }, topBar = {
                    if (isBarsVisible) {
                        MainTopBar(
                            onSearchClicked = {
                                when (tabNavigator.current) {
                                    is PostsTab -> {
                                        navigator.push(SearchScreen)
                                        onNavigation(false)
                                    }
                                    is MaterialTab -> {
                                        /*TODO*/
                                    }
                                    is CommunityMembersTab -> {
                                        /*TODO*/
                                    }
                                }
                            },
                            onNotificationClicked = { /*TODO*/ },
                            onNavDrawerIconClicked = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            })
                    }
                }, bottomBar = {
                    if (isBarsVisible) {
                        NavigationBar {
                            BottomTabNavigationItem(tab = PostsTab(communityId, onNavigation))
                            BottomTabNavigationItem(tab = MaterialTab(communityId))
                            BottomTabNavigationItem(tab = CommunityMembersTab(communityId))
                        }
                    }
                })
            }


        }
    }
}

enum class CommunityHomeTab {
    POSTS, MATERIALS, MEMBERS
}
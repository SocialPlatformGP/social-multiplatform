package com.gp.socialapp.presentation.community.communityhome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.seiko.imageloader.ui.AutoSizeImage
import kotlinx.coroutines.launch


data class CommunityHomeContainer(
    val communities: List<Community>,
    val user: User,
    val onAction: (HomeUiAction) -> Unit,
    val communityId: String
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
                    navigator.replaceAll(HomeContainer(false))
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
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.padding(8.dp).fillMaxSize()
                    ) {
                        println("Drawer content")
                        AutoSizeImage(
                            url = user.profilePictureURL,
                            contentDescription = "user image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                                .size(64.dp).clip(CircleShape)
                        )
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
                                navigator.replaceAll(HomeContainer(false))
                            }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            "Communities",
                            fontSize = 20.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        ) {
                            items(communities) {
                                NavigationDrawerItem(label = { Text(text = it.name) },
                                    selected = it.id == communityId,
                                    onClick = {
                                        communityId = it.id
                                        onAction(
                                            HomeUiAction.OnCommunityClicked(
                                                it.id
                                            )
                                        )
                                    }
                                )
                            }
                        }
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
            TabNavigator(PostsTab(communityId, onNavigation)) {
                Scaffold(content = {
                    Column(
                        modifier = Modifier.padding(it)
                    ) {
                        CurrentTab()
                    }
                }, topBar = {
                    if (isBarsVisible) MainTopBar(
                        {},
                        {},
                        onNavDrawerIconClicked = { onAction(HomeUiAction.OnOpenDrawer) }
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

}
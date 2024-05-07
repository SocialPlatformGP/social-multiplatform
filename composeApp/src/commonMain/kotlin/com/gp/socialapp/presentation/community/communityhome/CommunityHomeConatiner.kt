package com.gp.socialapp.presentation.community.communityhome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.navigation.tabs.AssignmentsTab
import com.gp.socialapp.navigation.tabs.CommunityMembersTab
import com.gp.socialapp.navigation.tabs.CreatorAssignmentTab
import com.gp.socialapp.navigation.tabs.MaterialTab
import com.gp.socialapp.navigation.tabs.PostsTab
import com.gp.socialapp.navigation.util.BottomTabNavigationItem
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.community.communityhome.components.CommunityHomeNavDrawer
import com.gp.socialapp.presentation.community.communityhome.components.MainTopBar
import com.gp.socialapp.presentation.home.container.HomeContainer
import com.gp.socialapp.presentation.post.search.SearchScreen
import com.gp.socialapp.presentation.settings.MainSettingsScreen
import kotlinx.coroutines.launch


data class CommunityHomeContainer(
    val communityId: String,
    val startingTab: CommunityHomeTab = CommunityHomeTab.POSTS
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<CommunityHomeContainerScreenModel>()
        LifecycleEffect(
            onStarted = {
                screenModel.init(communityId)
            },
            onDisposed = {
                screenModel.dispose()
            }
        )
        val state by screenModel.uiState.collectAsState()
        if (state.isLoggedOut) {
            navigator.replaceAll(LoginScreen)
        }
        CommunityHomeContainerContent(
            currentUser = state.currentUser,
            userCommunities = state.userCommunities,
            onNavigateToHome = { navigator.replaceAll(HomeContainer()) },
            onNavigateToSearch = { navigator.push(SearchScreen) },
            onNavigateToSettings = { navigator.push(MainSettingsScreen) },
            onLogout = { screenModel.logout() }
        )

    }

    @Composable
    fun CommunityHomeContainerContent(
        modifier: Modifier = Modifier,
        currentUser: User,
        userCommunities: List<Community>,
        onNavigateToHome: () -> Unit,
        onNavigateToSettings: () -> Unit,
        onNavigateToSearch: () -> Unit,
        onLogout: () -> Unit,
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var isBarsVisible by remember { mutableStateOf(true) }
        val onNavigation: (Boolean) -> Unit = { isBarsVisible = it }
        val defaultTab = when (startingTab) {
            CommunityHomeTab.POSTS -> PostsTab(communityId, onNavigation)
            CommunityHomeTab.MATERIALS -> MaterialTab(communityId)
            CommunityHomeTab.MEMBERS -> CommunityMembersTab(communityId)
        }
        CommunityHomeNavDrawer(
            modifier = modifier,
            drawerState = drawerState,
            user = currentUser,
            communityId = communityId,
            communities = userCommunities,
            onNavigateToHome = onNavigateToHome,
            onNavigateToSettings = onNavigateToSettings,
            onLogout = onLogout
        ) {
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
                                        onNavigateToSearch()
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
                            if (userCommunities.find { it.id == communityId }?.members?.get(currentUser.id) == true ) {
                                BottomTabNavigationItem(
                                    tab = CreatorAssignmentTab(communityId)
                                )
                            }
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
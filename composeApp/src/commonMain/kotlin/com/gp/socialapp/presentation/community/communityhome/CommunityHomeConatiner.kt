package com.gp.socialapp.presentation.community.communityhome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.navigation.tabs.AssignmentsTab
import com.gp.socialapp.navigation.tabs.CommunityMembersTab
import com.gp.socialapp.navigation.tabs.CreatorGradesTab
import com.gp.socialapp.navigation.tabs.MaterialTab
import com.gp.socialapp.navigation.tabs.PostsTab
import com.gp.socialapp.navigation.util.BottomTabNavigationItem
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.community.communityhome.components.CommunitySideMenu
import com.gp.socialapp.presentation.community.communityhome.components.MainTopBar
import com.gp.socialapp.presentation.home.container.HomeContainer
import com.gp.socialapp.presentation.post.search.SearchScreen
import com.gp.socialapp.presentation.settings.MainSettingsScreen
import com.gp.socialapp.util.clickableWithoutRipple


data class CommunityHomeContainer(
    val communityId: String, val startingTab: CommunityHomeTab = CommunityHomeTab.POSTS
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<CommunityHomeContainerScreenModel>()
        LifecycleEffect(onStarted = {
            screenModel.init(communityId)
        }, onDisposed = {
            screenModel.dispose()
        })
        val state by screenModel.uiState.collectAsState()
        if (state.isLoggedOut) {
            navigator.replaceAll(LoginScreen)
        }
        CommunityHomeContainerContent(currentUser = state.currentUser,
            userCommunities = state.userCommunities,
            onNavigateToHome = { navigator.replaceAll(HomeContainer()) },
            onNavigateToSearch = { navigator.push(SearchScreen) },
            onNavigateToSettings = { navigator.push(MainSettingsScreen) },
            onLogout = { screenModel.logout() })

    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
        var isBarsVisible by remember { mutableStateOf(true) }
        val onNavigation: (Boolean) -> Unit = { isBarsVisible = it }
        val defaultTab = when (startingTab) {
            CommunityHomeTab.POSTS -> PostsTab(communityId, onNavigation)
            CommunityHomeTab.MATERIALS -> MaterialTab(communityId)
            CommunityHomeTab.MEMBERS -> CommunityMembersTab(communityId)
        }
        val windowSizeClass = calculateWindowSizeClass()
        val isDesktop = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded
        var showDrawer by remember { mutableStateOf(false) }
        val mainContentAlpha by animateFloatAsState(if (showDrawer) 0.6f else 1f)
        val menuTabs = mutableListOf<Tab>().apply {
            add(PostsTab(communityId, onNavigation))
            add(MaterialTab(communityId))
            if (userCommunities.find { it.id == communityId }?.members?.get(currentUser.id) == true) {
                add(AssignmentsTab(onNavigation, communityId))
            }
            add(CommunityMembersTab(communityId))
            if (userCommunities.find { it.id == communityId }?.members?.get(currentUser.id) == true) {
                add(CreatorGradesTab(communityId))
            }
        }

        TabNavigator(defaultTab) { tabNavigator ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Row {
                    if (isDesktop) {
                        CommunitySideMenu(
                            user = currentUser,
                            onLogout = onLogout,

                            onNavigateToSettings = {
                                onNavigateToSettings()
                            },
                            onNavigateToHome = {
                                onNavigateToHome()
                            },
                            windowWidthSizeClass = windowSizeClass.widthSizeClass,
                            menuTabs = menuTabs,
                            isDesktop = true
                        )
                    }
                    Scaffold(
                        modifier = Modifier.fillMaxSize().alpha(mainContentAlpha)
                            .clickableWithoutRipple { showDrawer = false },
                        content = { paddingValues ->

                            Column(
                                modifier = Modifier.padding(
                                    if (isBarsVisible) paddingValues else PaddingValues(
                                        0.dp
                                    )
                                ),

                                ) {
                                CurrentTab()
                            }
                        }, topBar = {
                            if (isBarsVisible) {
                                MainTopBar(onSearchClicked = {
                                    when (tabNavigator.current) {
                                        is PostsTab -> {
                                            onNavigateToSearch()
                                            onNavigation(false)
                                        }

                                        is MaterialTab -> {/*TODO*/
                                        }

                                        is CommunityMembersTab -> {/*TODO*/
                                        }
                                    }
                                }, onNotificationClicked = { /*TODO*/ }, onNavDrawerIconClicked = {
                                    showDrawer = !showDrawer
                                })
                            }
                        }, bottomBar = {
                            if (isBarsVisible && !isDesktop) {
                                NavigationBar {
                                    BottomTabNavigationItem(
                                        tab = PostsTab(
                                            communityId,
                                            onNavigation
                                        )
                                    )
                                    BottomTabNavigationItem(tab = MaterialTab(communityId))
                                    if (userCommunities.find { it.id == communityId }?.members?.get(
                                            currentUser.id
                                        ) == true
                                    ) {
                                        BottomTabNavigationItem(
                                            tab = AssignmentsTab(onNavigation, communityId)
                                        )
                                    }
                                    BottomTabNavigationItem(tab = CommunityMembersTab(communityId))
                                    if (userCommunities.find { it.id == communityId }?.members?.get(
                                            currentUser.id
                                        ) == true
                                    ) {
                                        BottomTabNavigationItem(
                                            tab = CreatorGradesTab(communityId)
                                        )
                                    }
                                }
                            }
                        })
                }
                AnimatedVisibility(
                    visible = showDrawer,
                    enter = slideInHorizontally(animationSpec = tween()) { -it },
                    exit = slideOutHorizontally(animationSpec = if (isDesktop) snap() else spring()) { -it }
                ) {
                    CommunitySideMenu(
                        user = currentUser,
                        onLogout = onLogout,

                        onNavigateToSettings = {
                            onNavigateToSettings()
                        },
                        onNavigateToHome = {
                            onNavigateToHome()
                        },
                        windowWidthSizeClass = windowSizeClass.widthSizeClass,
                        backgroundColor = MaterialTheme.colorScheme.surface

                    )
                }
            }
        }
    }
}

enum class CommunityHomeTab {
    POSTS, MATERIALS, MEMBERS
}
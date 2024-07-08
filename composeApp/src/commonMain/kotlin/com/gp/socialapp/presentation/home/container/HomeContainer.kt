package com.gp.socialapp.presentation.home.container

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
import androidx.compose.runtime.LaunchedEffect
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
import com.gp.socialapp.navigation.tabs.AssignmentsTab
import com.gp.socialapp.navigation.tabs.CalendarTab
import com.gp.socialapp.navigation.tabs.ChatTab
import com.gp.socialapp.navigation.tabs.CommunitiesTab
import com.gp.socialapp.navigation.tabs.GradesTab
import com.gp.socialapp.navigation.util.BottomTabNavigationItem
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.auth.userinfo.UserInformationScreen
import com.gp.socialapp.presentation.home.components.HomeTopBar
import com.gp.socialapp.presentation.home.components.SideMenu
import com.gp.socialapp.presentation.settings.MainSettingsScreen
import com.gp.socialapp.util.clickableWithoutRipple

data class HomeContainer(
    val startingTab: HomeTab = HomeTab.COMMUNITIES,
) : Screen {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        var navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<HomeContainerScreenModel>()
        val state by screenModel.uiState.collectAsState()
        var barsVisibility by remember { mutableStateOf(true) }
        val windowSizeClass = calculateWindowSizeClass()
        val isDesktop = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded
        var showDrawer by remember { mutableStateOf(false) }
        val mainContentAlpha by animateFloatAsState(if (showDrawer) 0.6f else 1f)
        LaunchedEffect(key1 = isDesktop) {
            if (isDesktop) {
                showDrawer = false
            }
        }
        LifecycleEffect(
            onStarted = { screenModel.init() },
            onDisposed = { screenModel.onDispose() })
        if (!state.currentUser.isDataComplete && state.currentUser.id.isNotBlank()) {
            navigator.replaceAll(UserInformationScreen(state.currentUser))
        }
        val onNavigation: (Boolean) -> Unit = { barsVisibility = it }
        val menuTabs = listOf(
            CommunitiesTab(onNavigation),
            ChatTab(onNavigation),
            AssignmentsTab(onNavigation),
            CalendarTab(onNavigation),
            GradesTab
        )
        if (state.isLoggedOut) {
            navigator.replaceAll(LoginScreen)
        }
        val defaultTab = when (startingTab) {
            HomeTab.CHAT -> ChatTab(onNavigation)
            HomeTab.ASSIGNMENTS -> AssignmentsTab(onNavigation)
            HomeTab.COMMUNITIES -> CommunitiesTab(onNavigation)
            HomeTab.CALENDAR -> CalendarTab(onNavigation)
            HomeTab.GRADES -> GradesTab
        }
        val MainContent: @Composable () -> Unit = {
                Scaffold(
                    modifier = Modifier.fillMaxSize().alpha(mainContentAlpha)
                        .clickableWithoutRipple { showDrawer = false },
                    content = {
                        Column(
                            modifier = Modifier.padding(
                                if (barsVisibility) it else PaddingValues(
                                    0.dp
                                )
                            ),

                            ) {
                            CurrentTab()
                        }
                    },
                    topBar = {
                        if (barsVisibility)
                            HomeTopBar(
                                isDesktop = isDesktop,
                                onDrawerIconClicked = {
                                    showDrawer = !showDrawer
                                })
                    },

                    bottomBar = {

                        if (barsVisibility && !isDesktop) NavigationBar {
                            menuTabs.forEach{
                                BottomTabNavigationItem(it)
                            }
                        }
                    },
                )
        }
        TabNavigator(defaultTab) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Row {
                    if (isDesktop && barsVisibility) {
                        SideMenu(
                            userProfilePictureUrl = state.currentUser.profilePictureURL,
                            userName = state.currentUser.name,
                            userEmail = state.currentUser.email,
                            onLogOut = {
                                screenModel.logout()
                                navigator.replaceAll(LoginScreen)
                            },
                            onNavigateToSettings = {
                                navigator.push(MainSettingsScreen)
                            },
                            windowWidthSizeClass = windowSizeClass.widthSizeClass,
                            menuTabs = menuTabs,
                            isDesktop = true,
                        )
                    }
                    MainContent()
                }
                AnimatedVisibility(
                    visible = showDrawer,
                    enter = slideInHorizontally(animationSpec = tween()) { -it },
                    exit = slideOutHorizontally(animationSpec = if (isDesktop) snap() else spring()) { -it }
                ) {
                    SideMenu(
                        userProfilePictureUrl = state.currentUser.profilePictureURL,
                        userName = state.currentUser.name,
                        userEmail = state.currentUser.email,
                        onLogOut = {
                            screenModel.logout()
                            navigator.replaceAll(LoginScreen)
                        },
                        onNavigateToSettings = {
                            navigator.push(MainSettingsScreen)
                        },
                        windowWidthSizeClass = windowSizeClass.widthSizeClass,
                        backgroundColor = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

enum class HomeTab {
    CHAT,
    ASSIGNMENTS,
    COMMUNITIES,
    CALENDAR,
    GRADES
}
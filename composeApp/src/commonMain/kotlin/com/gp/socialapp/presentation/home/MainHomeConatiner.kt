package com.gp.socialapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
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

object HomeContainer : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<HomeScreenModel>()
        val state by screenModel.uiState.collectAsState()
        var barsVisibility by remember { mutableStateOf(true) }
        LifecycleEffect(onStarted = { screenModel.init() }, onDisposed = { screenModel.dispose() })
        if (!state.user.isDataComplete && state.user.id.isNotBlank()) {
            navigator.replaceAll(UserInformationScreen(state.user))
        }
        if (state.loggedOut) {
            navigator.replaceAll(LoginScreen)

        }
        val onAction: (HomeUiAction) -> Unit = {
            when (it) {
                HomeUiAction.OnUserLogout -> {
                    screenModel.userLogout()
                }

                else -> Unit
            }
        }
        TabNavigator(CommunitiesTab {
            barsVisibility = it
        }) {
            Scaffold(
                content = {
                    Column(
                        modifier = Modifier.padding(it)
                    ) {
                        CurrentTab()
                    }
                },
                topBar = {
                    if (barsVisibility) HomeTopBar(
                        action = onAction
                    )
                },

                bottomBar = {
                    if (barsVisibility) NavigationBar {
                        BottomTabNavigationItem(tab = ChatTab)
                        BottomTabNavigationItem(tab = CalendarTab)
                        BottomTabNavigationItem(tab = CommunitiesTab {
                            barsVisibility = it
                        })
                        BottomTabNavigationItem(tab = GradesTab)
                        BottomTabNavigationItem(tab = AssignmentsTab)
                    }
                },
            )
        }
    }
}
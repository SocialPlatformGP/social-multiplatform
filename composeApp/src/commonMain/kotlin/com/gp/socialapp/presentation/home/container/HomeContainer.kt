package com.gp.socialapp.presentation.home.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.collectAsState
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
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
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
import com.gp.socialapp.presentation.settings.MainSettingsScreen
import com.seiko.imageloader.ui.AutoSizeImage
import kotlinx.coroutines.launch

data class HomeContainer(
    val startingTab: HomeTab = HomeTab.COMMUNITIES
) : Screen {
    @Composable
    override fun Content() {
        var navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<HomeContainerScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var barsVisibility by remember { mutableStateOf(true) }
        LifecycleEffect(
            onStarted = { screenModel.init() },
            onDisposed = { screenModel.onDispose() })
        if (!state.currentUser.isDataComplete && state.currentUser.id.isNotBlank()) {
            navigator.replaceAll(UserInformationScreen(state.currentUser))
        }
        val onNavigation: (Boolean) -> Unit = { barsVisibility = it }
        if (state.isLoggedOut) {
            navigator.replaceAll(LoginScreen)
        }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.padding(8.dp).fillMaxSize()
                    ) {
                        if (state.currentUser.profilePictureURL.isNotBlank())
                            AutoSizeImage(
                                url = state.currentUser.profilePictureURL,
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
                                    text = if (state.currentUser.name.isNotBlank()) state.currentUser.name[0].toString() else "u"
                                        .uppercase(),
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            state.currentUser.name,
                            fontSize = 24.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            state.currentUser.email,
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
                            selected = true,
                            onClick = {}
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
                                    screenModel.logout()
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
                                    navigator.push(MainSettingsScreen)
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


            val defaultTab = when (startingTab) {
                HomeTab.CHAT -> ChatTab(onNavigation)
                HomeTab.ASSIGNMENTS -> AssignmentsTab(onNavigation)
                HomeTab.COMMUNITIES -> CommunitiesTab(onNavigation)
                HomeTab.CALENDAR -> CalendarTab(onNavigation)
                HomeTab.GRADES -> GradesTab
            }
            TabNavigator(defaultTab) {
                Scaffold(
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
                                onDrawerIconClicked = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                })
                    },

                    bottomBar = {

                        if (barsVisibility) NavigationBar {
                            BottomTabNavigationItem(tab = ChatTab(onNavigation))
                            BottomTabNavigationItem(tab = AssignmentsTab(onNavigation))
                            BottomTabNavigationItem(
                                tab = CommunitiesTab(
                                    onNavigation
                                )
                            )
                            BottomTabNavigationItem(tab = CalendarTab(onNavigation))
                            BottomTabNavigationItem(tab = GradesTab)
                        }
                    },
                )
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
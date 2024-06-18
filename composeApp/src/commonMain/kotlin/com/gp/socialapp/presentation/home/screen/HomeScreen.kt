package com.gp.socialapp.presentation.home.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
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
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.auth.userinfo.UserInformationScreen
import com.gp.socialapp.presentation.chat.home.components.SingleFab
import com.gp.socialapp.presentation.community.communityhome.CommunityHomeContainer
import com.gp.socialapp.presentation.community.communityhome.CommunityHomeTab
import com.gp.socialapp.presentation.community.createcommunity.CreateCommunityScreen
import com.gp.socialapp.presentation.community.editcommunity.EditCommunityScreen
import com.gp.socialapp.presentation.home.components.CommunityOptionsCompactMenu
import com.gp.socialapp.presentation.home.components.CommunityOptionsExpandedMenu
import com.gp.socialapp.presentation.home.components.ConfirmLeaveCommunityDialog
import com.gp.socialapp.presentation.home.components.FabItem
import com.gp.socialapp.presentation.home.components.HomeContent
import com.gp.socialapp.presentation.home.components.HomeFab
import com.gp.socialapp.presentation.home.components.JoinCommunityDialog
import com.gp.socialapp.presentation.home.components.MultiFloatingActionButton
import com.gp.socialapp.presentation.home.components.OptionItem
import com.gp.socialapp.util.copyToClipboard
import com.mohamedrejeb.calf.core.LocalPlatformContext
import kotlinx.coroutines.launch

data class HomeScreen(
    val onBottomBarVisibilityChanged: (Boolean) -> Unit,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<HomeScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val context = LocalPlatformContext.current
        LifecycleEffect(
            onStarted = {
                onBottomBarVisibilityChanged(true)
                screenModel.init()
            }, onDisposed = {
//                onBottomBarVisibilityChanged(false)
            })
        if (!state.user.isDataComplete && state.user.id.isNotBlank()) {
            navigator.replaceAll(UserInformationScreen(state.user))
        }
        if (state.loggedOut) {
            screenModel.dispose()
            navigator.replaceAll(LoginScreen)

        }
        HomeScreenContent(
            state = state,
            onAction = { it ->
                when (it) {
                    is HomeUiAction.OnCommunityClicked -> {
                        onBottomBarVisibilityChanged(false)
                        navigator.replaceAll(
                            CommunityHomeContainer(
                                it.communityId
                            )
                        )
                    }

                    is HomeUiAction.OnLeaveCommunityClicked -> {
                        screenModel.communityLogout(it.id)
                    }

                    HomeUiAction.OnCreateCommunityClicked -> navigator.push(
                        CreateCommunityScreen
                    )

                    is HomeUiAction.OnJoinCommunityClicked -> {
                        screenModel.joinCommunity(it.code)
                    }

                    HomeUiAction.OnProfileClicked -> Unit //TODO( Navigate to profile screen)
                    HomeUiAction.OnUserLogout -> {
                        screenModel.userLogout()
                    }

                    is HomeUiAction.OnDeleteCommunityClicked -> {
                        screenModel.deleteCommunity(it.communityId)
                    }

                    is HomeUiAction.OnEditCommunityClicked -> {
                        println("HomeScreen OnEditCommunityClicked: ${it.community}")
                        navigator.push(EditCommunityScreen(it.community))
                    }

                    is HomeUiAction.OnManageMembersClicked -> {
                        navigator.push(
                            CommunityHomeContainer(
                                communityId = it.communityId,
                                startingTab = CommunityHomeTab.MEMBERS
                            )
                        )
                    }

                    is HomeUiAction.OnShareJoinCodeClicked -> {
                        context.copyToClipboard(it.code)
                    }

                    is HomeUiAction.OnViewMembersClicked -> {
                        navigator.push(
                            CommunityHomeContainer(
                                communityId = it.communityId,
                                startingTab = CommunityHomeTab.MEMBERS,
                            )
                        )
                    }

                    is HomeUiAction.OnCommunityMaterialClicked -> {
                        navigator.push(
                            CommunityHomeContainer(
                                communityId = it.communityId,
                                startingTab = CommunityHomeTab.MATERIALS,
                            )
                        )
                    }
                    is HomeUiAction.OnCommunityMembersClicked -> {
                        navigator.push(
                            CommunityHomeContainer(
                                communityId = it.communityId,
                                startingTab = CommunityHomeTab.MEMBERS,
                            )
                        )
                    }
                    else -> Unit
                }
            })
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    fun HomeScreenContent(
        state: HomeUiState, onAction: (HomeUiAction) -> Unit
    ) {
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState()
        var showOptionsMenu by remember { mutableStateOf(false) }
        var joinCommunityDialogState by remember { mutableStateOf(false) }
        var confirmLogoutDialogState by remember { mutableStateOf(false) }
        var communityId by remember { mutableStateOf("") }
        var clickedCommunity by remember { mutableStateOf(Community()) }
        val windowSizeClass = calculateWindowSizeClass()
        var newOnAction: (HomeUiAction) -> Unit = {
            when (it) {
                is HomeUiAction.OnLeaveCommunityClicked -> {
                    confirmLogoutDialogState = true
                    communityId = it.id
                }

                is HomeUiAction.OnOptionsMenuClicked -> {
                    clickedCommunity = it.community
                    showOptionsMenu = true
                    scope.launch { sheetState.show() }
                }
                else -> onAction(it)
            }
        }
        val options = if (clickedCommunity.members.getOrElse(state.user.id) { false }) {
            listOf(
                OptionItem("Share Join Code") {
                    newOnAction(HomeUiAction.OnShareJoinCodeClicked(clickedCommunity.code))
                },
                OptionItem("Manage Members") {
                    newOnAction(HomeUiAction.OnManageMembersClicked(clickedCommunity.id))
                },
                OptionItem("Edit Community") {
                    newOnAction(HomeUiAction.OnEditCommunityClicked(clickedCommunity))
                },
                OptionItem("Leave Community") {
                    newOnAction(HomeUiAction.OnLeaveCommunityClicked(clickedCommunity.id))
                },
                OptionItem("Delete Community") {
                    newOnAction(HomeUiAction.OnDeleteCommunityClicked(clickedCommunity.id))
                }
            )
        } else {
            listOf(
                OptionItem("Share Join Code") {
                    newOnAction(HomeUiAction.OnShareJoinCodeClicked(clickedCommunity.code))
                },
                OptionItem("View Members") {
                    newOnAction(HomeUiAction.OnViewMembersClicked(clickedCommunity.id))
                },
                OptionItem("Leave Community") {
                    newOnAction(HomeUiAction.OnLeaveCommunityClicked(clickedCommunity.id))
                }
            )
        }
        var fabState = remember { mutableStateOf(false) }
        Scaffold(
            floatingActionButton = {
                val fabItems = listOf(
                    FabItem(
                        label = "Create Community",
                        icon = Icons.Default.GroupAdd,
                        onFabItemClicked = {
                            newOnAction(HomeUiAction.OnCreateCommunityClicked)
                        }
                    ),
                    FabItem(
                        label = "Join Community",
                        icon = Icons.AutoMirrored.Filled.Login,
                        onFabItemClicked = {
                            joinCommunityDialogState = true
                        }
                    )
                )
                MultiFloatingActionButton(
                    fabIcon = Icons.Default.Add,
                    items = fabItems,
                )
            }) { padding ->
            if (showOptionsMenu) {
                when(windowSizeClass.widthSizeClass){
                    WindowWidthSizeClass.Compact -> {
                        CommunityOptionsCompactMenu(
                            sheetState = sheetState,
                            options = options,
                            onDismiss = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showOptionsMenu = false
                                    }
                                }
                            }
                        )
                    }
                    else -> {
                        CommunityOptionsExpandedMenu(
                            options = options,
                            onDismiss = {
                                showOptionsMenu = false
                            }
                        )
                    }
                }
            }
            if (joinCommunityDialogState) {
                JoinCommunityDialog(onDismiss = {
                    joinCommunityDialogState = false
                }, onJoin = {
                    joinCommunityDialogState = false
                    onAction(HomeUiAction.OnJoinCommunityClicked(it))
                })
            }
            if (confirmLogoutDialogState) {
                ConfirmLeaveCommunityDialog(onDismiss = {
                    confirmLogoutDialogState = false
                }, onConfirm = {
                    onAction(HomeUiAction.OnLeaveCommunityClicked(communityId))
                    confirmLogoutDialogState = false
                })
            }
            HomeContent(
                modifier = Modifier.padding(padding),
                communities = state.communities,
                action = newOnAction,
                windowWidthSizeClass = windowSizeClass.widthSizeClass
            )
        }
    }
}




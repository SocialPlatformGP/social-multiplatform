package com.gp.socialapp.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.gp.socialapp.presentation.home.components.CommunityOptionsBottomSheet
import com.gp.socialapp.presentation.home.components.ConfirmLogoutDialog
import com.gp.socialapp.presentation.home.components.HomeContent
import com.gp.socialapp.presentation.home.components.HomeFab
import com.gp.socialapp.presentation.home.components.JoinCommunityDialog
import com.gp.socialapp.presentation.home.components.OptionItem
import kotlinx.coroutines.launch
import org.jetbrains.skiko.ClipboardManager

data class HomeScreen(
    val onBottomBarVisibilityChanged: (Boolean) -> Unit,
    val action: (HomeUiAction) -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<HomeScreenModel>()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect(
            onStarted = {
                onBottomBarVisibilityChanged(true)
                screenModel.init()
            }, onDisposed = {
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
            onAction = { acions ->
                when (acions) {
                    is HomeUiAction.OnCommunityClicked -> {
                        onBottomBarVisibilityChanged(false)
                        navigator.replaceAll(
                            CommunityHomeContainer(
                                communities = state.communities,
                                user = state.user,
                                onAction = { action(it) },
                                acions.communityId
                            )
                        )
                    }

                    is HomeUiAction.OnCommunityLogout -> {
                        screenModel.communityLogout(acions.id)
                    }

                    HomeUiAction.OnCreateCommunityClicked -> navigator.push(
                        CreateCommunityScreen
                    )

                    is HomeUiAction.OnJoinCommunityClicked -> {
                        screenModel.joinCommunity(acions.code)
                    }

                    HomeUiAction.OnProfileClicked -> Unit //TODO( Navigate to profile screen)
                    HomeUiAction.OnUserLogout -> {
                        screenModel.userLogout()
                    }

                is HomeUiAction.OnDeleteCommunityClicked -> {
                    screenModel.deleteCommunity(it.communityId)
                }

                is HomeUiAction.OnEditCommunityClicked -> {
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
                    val clipboardManager = ClipboardManager()
                    clipboardManager.setText(it.code)
                }

                is HomeUiAction.OnViewMembersClicked -> {
                    navigator.push(
                        CommunityHomeContainer(
                            communityId = it.communityId,
                            startingTab = CommunityHomeTab.MEMBERS
                        )
                    )
                }

                else -> Unit
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeUiState, onAction: (HomeUiAction) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var joinCommunityDialogState by remember { mutableStateOf(false) }
    var confirmLogoutDialogState by remember { mutableStateOf(false) }
    var communityId by remember { mutableStateOf("") }
    var clickedCommunity by remember { mutableStateOf(Community()) }
    val options = if (clickedCommunity.members.getOrElse(state.user.id) { false }) {
        listOf(
            OptionItem("Share Join Code") {
                onAction(HomeUiAction.OnShareJoinCodeClicked(clickedCommunity.code))
            },
            OptionItem("Manage Members") {
                onAction(HomeUiAction.OnManageMembersClicked(clickedCommunity.id))
            },
            OptionItem("Edit Community") {
                onAction(HomeUiAction.OnEditCommunityClicked(clickedCommunity))
            },
            OptionItem("Leave Community") {
                onAction(HomeUiAction.OnCommunityLogout(clickedCommunity.id))
            },
            OptionItem("Delete Community") {
                onAction(HomeUiAction.OnDeleteCommunityClicked(clickedCommunity.id))
            }
        )
    } else {
        listOf(
            OptionItem("Share Join Code") {
                onAction(HomeUiAction.OnShareJoinCodeClicked(clickedCommunity.code))
            },
            OptionItem("View Members") {
                onAction(HomeUiAction.OnViewMembersClicked(clickedCommunity.id))
            },
            OptionItem("Leave Community") {
                onAction(HomeUiAction.OnCommunityLogout(clickedCommunity.id))
            }
        )
    }
    var fabState = remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            if (!fabState.value) SingleFab(
                fabState, Icons.Default.Add
            )
            else {
                HomeFab(
                    fabState,
                    onCreateCommunityClicked = {
                        onAction(HomeUiAction.OnCreateCommunityClicked)
                    },
                    onJoinCommunityClicked = {
                        joinCommunityDialogState = true
                    }
                )
            }
        }) { padding ->
        if (showBottomSheet) {
            CommunityOptionsBottomSheet(
                sheetState = sheetState,
                options = options,
                onDismiss = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }
            )
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
            ConfirmLogoutDialog(onDismiss = {
                confirmLogoutDialogState = false
            }, onConfirm = {
                onAction(HomeUiAction.OnCommunityLogout(communityId))
                confirmLogoutDialogState = false
            })
        }
        HomeContent(modifier = Modifier.padding(padding),
            communities = state.communities,
            action = {
                when (it) {
                    is HomeUiAction.OnCommunityLogout -> {
                        confirmLogoutDialogState = true
                        communityId = it.id
                    }

                    is HomeUiAction.OnOptionsMenuClicked -> {
                        clickedCommunity = it.community
                        showBottomSheet = true
                        scope.launch { sheetState.show() }
                    }

                    else -> onAction(it)
                }
            })
    }
}


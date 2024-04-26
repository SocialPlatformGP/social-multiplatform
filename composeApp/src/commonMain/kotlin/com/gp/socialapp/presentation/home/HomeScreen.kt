package com.gp.socialapp.presentation.home

import androidx.compose.foundation.layout.padding
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
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.community.createcommunity.CreateCommunityScreen
import com.gp.socialapp.presentation.home.components.ConfirmLogoutDialog
import com.gp.socialapp.presentation.home.components.HomeBottomSheet
import com.gp.socialapp.presentation.home.components.HomeContent
import com.gp.socialapp.presentation.home.components.HomeFab
import com.gp.socialapp.presentation.home.components.HomeTopBar
import com.gp.socialapp.presentation.home.components.JoinCommunityDialog
import com.gp.socialapp.presentation.main.MainContainer
import com.gp.socialapp.presentation.main.userinfo.UserInformationScreen
import kotlinx.coroutines.launch

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<HomeScreenModel>()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect(
            onStarted = { screenModel.init() },
            onDisposed = { screenModel.dispose() }
        )
        if (!state.user.isDataComplete && state.user.id.isNotBlank()) {
            navigator.replaceAll(UserInformationScreen(state.user))
        }
        if (state.loggedOut) {
            navigator.replaceAll(LoginScreen)

        }
        HomeScreenContent(state = state, action = {
            when (it) {
                is HomeUiAction.OnCommunityClicked -> navigator.replaceAll(
                    MainContainer(
                        state.user
                    )
                )

                is HomeUiAction.OnCommunityLogout -> {
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
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeUiState, action: (HomeUiAction) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var joinCommunityDialogState by remember { mutableStateOf(false) }
    var confirmLogoutDialogState by remember { mutableStateOf(false) }
    var communityId by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            HomeTopBar(
                action = action
            )
        }, floatingActionButton = {
            HomeFab {
                showBottomSheet = true
            }
        }) { padding ->
        if (showBottomSheet) {
            HomeBottomSheet(closeSheet = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }, sheetState = sheetState, action = action, onJoinCommunityClicked = {
                joinCommunityDialogState = true
            })
        }
        if (joinCommunityDialogState) {
            JoinCommunityDialog(onDismiss = {
                joinCommunityDialogState = false
            }, onJoin = {
                joinCommunityDialogState = false
                action(HomeUiAction.OnJoinCommunityClicked(it))
            })
        }
        if (confirmLogoutDialogState) {
            ConfirmLogoutDialog(onDismiss = {
                confirmLogoutDialogState = false
            }, onConfirm = {
                action(HomeUiAction.OnCommunityLogout(communityId))
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

                    else -> action(it)
                }
            })
    }


}


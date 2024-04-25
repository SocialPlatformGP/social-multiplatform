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
import com.gp.socialapp.presentation.community.createcommunity.CreateCommunityScreen
import com.gp.socialapp.presentation.home.components.HomeBottomSheet
import com.gp.socialapp.presentation.home.components.HomeContent
import com.gp.socialapp.presentation.home.components.HomeFab
import com.gp.socialapp.presentation.home.components.HomeTopBar
import com.gp.socialapp.presentation.home.components.JoinCommunityDialog
import kotlinx.coroutines.launch

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<HomeScreenModel>()
        LifecycleEffect(onStarted = { screenModel.init() })
        val state = screenModel.uiState.collectAsState()
        HomeScreenContent(
            state = state.value,
            action = {
                when (it) {
                    is HomeUiAction.OnCommunityClicked -> Unit // TODO(Navigate to community screen)
                    is HomeUiAction.OnCommunityLogout -> Unit // TODO( Logout from community)
                    HomeUiAction.OnCreateCommunityClicked -> navigator.push(CreateCommunityScreen)
                    HomeUiAction.OnJoinCommunityClicked -> Unit//TODO( Join community)
                    HomeUiAction.OnProfileClicked -> Unit //TODO( Navigate to profile screen)
                    HomeUiAction.OnUserLogout -> Unit //TODO( Logout user)
                }
            }
        )
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
    var dialogState by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        HomeTopBar(
            user = state.user, action = action
        )
    }, floatingActionButton = {
        HomeFab {
            showBottomSheet = true
        }
    }) { padding ->
        if (showBottomSheet) {
            HomeBottomSheet(
                closeSheet = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
                sheetState = sheetState,
                action = action,
                onJoinCommunityClicked = {
                    dialogState = true
                }
            )
        }
        if (dialogState) {
            JoinCommunityDialog(
                onDismiss = {
                    dialogState = false
                },
                onJoin = {
                    dialogState = false
                }
            )
        }
        HomeContent(
            modifier = Modifier.padding(padding), communities = state.communities, action = action
        )
    }


}


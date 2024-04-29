package com.gp.socialapp.presentation.community.communitymembers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.presentation.community.communitymembers.components.CommunityMemberRequestsList
import com.gp.socialapp.presentation.community.communitymembers.components.CommunityMembersList
import com.gp.socialapp.presentation.community.communitymembers.components.CommunityMembersSection
import io.github.aakira.napier.Napier

data class CommunityMembersScreen(
    val communityId: String
) : Screen {
    @Composable
    override fun Content() {
        Napier.e("CommunityMembersScreen Content${communityId}")
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<CommunityMembersScreenModel>()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect(
            onStarted = {
                println("CommunityMembersScreen onStarted")
                screenModel.onInit(communityId)
            },
            onDisposed = { screenModel.onDispose() })
        CommunityMembersContent(
            requests = state.requests,
            communityName = state.communityName,
            admins = state.admins,
            isAdmin = state.admins.contains(state.currentUserId),
            onAction = { action ->
                when (action) {
                    is CommunityMembersUiAction.OnBackClicked -> navigator.pop()
                    else -> screenModel.handleUiAction(action)
                }
            },
            members = state.members
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CommunityMembersContent(
        modifier: Modifier = Modifier,
        requests: List<CommunityMemberRequest>,
        communityName: String,
        admins: List<String>,
        isAdmin: Boolean,
        onAction: (CommunityMembersUiAction) -> Unit,
        members: List<User>
    ) {
        Scaffold(modifier = modifier.padding(16.dp)) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                if (isAdmin && requests.isNotEmpty()) {
                    CommunityMembersSection(title = "Requests", body = {
                        CommunityMemberRequestsList(requests = requests,
                            onAcceptRequest = { id ->
                                onAction(
                                    CommunityMembersUiAction.OnAcceptRequest(
                                        id
                                    )
                                )
                            },
                            onDeclineRequest = { id ->
                                onAction(
                                    CommunityMembersUiAction.OnDeclineRequest(
                                        id
                                    )
                                )
                            },
                            onUserClicked = { id ->
                                onAction(
                                    CommunityMembersUiAction.OnUserClicked(
                                        id
                                    )
                                )
                            })
                    })
                }
                CommunityMembersSection(title = "Members", body = {
                    CommunityMembersList(members = members, admins = admins, onUserClicked = { id ->
                        onAction(
                            CommunityMembersUiAction.OnUserClicked(
                                id
                            )
                        )
                    })
                })
            }
        }
    }
}

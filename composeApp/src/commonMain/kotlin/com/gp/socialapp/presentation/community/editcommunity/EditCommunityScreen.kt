package com.gp.socialapp.presentation.community.editcommunity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.presentation.community.createcommunity.CreateCommunityAction
import com.gp.socialapp.presentation.community.createcommunity.EmailDomain
import com.gp.socialapp.presentation.community.createcommunity.components.AllowedEmailDomainsSection
import com.gp.socialapp.presentation.community.createcommunity.components.CommunityDescriptionSection
import com.gp.socialapp.presentation.community.createcommunity.components.CommunityNameSection
import com.gp.socialapp.presentation.community.createcommunity.components.SwitchSetting

data class EditCommunityScreen(private val community: Community): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<EditCommunityScreenModel>()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect (
            onStarted = {
                screenModel.init(community)
            },
            onDisposed = {
                screenModel.clear()
            }
        )
        if (state.isFinished){
            navigator.pop()
        } else {
            EditCommunityContent(
                onAction = { action ->
                    when (action) {
                        EditCommunityUiAction.OnBackClicked -> {
                            navigator.pop()
                        }
                        else -> screenModel.handleUiAction(action)
                    }
                },
                communityName = state.communityName,
                communityDescription = state.communityDescription,
                requireAdminApproval = state.requireAdminApproval,
                allowAnyEmailDomain = state.allowAnyEmailDomain,
                allowedEmailDomains = state.allowedEmailDomains
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditCommunityContent(
        modifier: Modifier = Modifier,
        onAction: (EditCommunityUiAction) -> Unit,
        communityName: String,
        communityDescription: String,
        requireAdminApproval: Boolean,
        allowAnyEmailDomain: Boolean,
        allowedEmailDomains: Set<EmailDomain>,
    ) {
        Scaffold (
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Edit Community")
                    },
                    navigationIcon = {
                        IconButton(onClick = { onAction(EditCommunityUiAction.OnBackClicked) }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        TextButton(onClick = { onAction(EditCommunityUiAction.OnSaveClicked) }) {
                            Text(text = "Save")
                        }
                    }
                )
            }
        ){ paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues).padding(horizontal = 16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CommunityNameSection(
                    name = communityName,
                    onUpdateName = { onAction(EditCommunityUiAction.OnUpdateCommunityName(it)) },
                    isError = false, /*TODO*/
                    onChangeError = { /*TODO*/ }
                )
                CommunityDescriptionSection(
                    description = communityDescription,
                    onDescriptionChange = {
                        onAction(EditCommunityUiAction.OnUpdateCommunityDescription(it))
                    },
                )
                Spacer(modifier = Modifier.size(16.dp))
                SwitchSetting(
                    title = "Require Admin Approval",
                    description = "Require admin approval before joining",
                    isChecked = requireAdminApproval,
                    onCheckedChange = {
                        onAction(EditCommunityUiAction.OnRequireAdminApprovalChanged(it))
                    },
                )
                Spacer(modifier = Modifier.size(16.dp))
                SwitchSetting(
                    title = "Allow any email domain",
                    description = "Allow any email domain to join",
                    isChecked = allowAnyEmailDomain,
                    onCheckedChange = {
                        onAction(EditCommunityUiAction.OnAllowAnyEmailDomainChanged(it))
                    },
                )
                if (!allowAnyEmailDomain) {
                    AllowedEmailDomainsSection(
                        modifier = Modifier.weight(1f),
                        allowedEmailDomains = allowedEmailDomains,
                        onAddEmailDomain = {
                            onAction(
                                EditCommunityUiAction.OnAddAllowedEmailDomain(it))
                        },
                        onRemoveEmailDomain = {
                            onAction(EditCommunityUiAction.OnRemoveAllowedEmailDomain(it))
                        },
                    )
                }
            }
        }
    }
}
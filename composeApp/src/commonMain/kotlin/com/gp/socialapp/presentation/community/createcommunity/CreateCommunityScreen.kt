package com.gp.socialapp.presentation.community.createcommunity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.community.createcommunity.components.AllowedEmailDomainsSection
import com.gp.socialapp.presentation.community.createcommunity.components.CommunityDescriptionSection
import com.gp.socialapp.presentation.community.createcommunity.components.CommunityNameSection
import com.gp.socialapp.presentation.community.createcommunity.components.SwitchSetting

object CreateCommunityScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<CreateCommunityScreenModel>()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect(
            onStarted = {
                screenModel.init()
            },
            onDisposed = {
                screenModel.dispose()
            }
        )
        if (state.createdCommunity != null) {
            navigator.pop()
        } else {
            CreateCommunityContent(
                onAction = { action ->
                    when (action) {
                        CreateCommunityAction.OnBackClicked -> {
                            navigator.pop()
                        }

                        else -> screenModel.handleUiAction(action)
                    }
                },
                communityName = state.communityName,
                communityDescription = state.communityDescription,
                requireAdminApproval = state.requireAdminApproval,
                allowAnyEmailDomain = state.allowAnyEmailDomain,
                allowedEmailDomains = state.allowedEmailDomains,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreateCommunityContent(
        modifier: Modifier = Modifier,
        onAction: (CreateCommunityAction) -> Unit,
        communityName: String,
        communityDescription: String,
        requireAdminApproval: Boolean,
        allowAnyEmailDomain: Boolean,
        allowedEmailDomains: Set<EmailDomain>,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Create Community")
                    },
                    navigationIcon = {
                        IconButton(onClick = { onAction(CreateCommunityAction.OnBackClicked) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {},
                )
            },
            modifier = modifier.fillMaxSize(),
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues).padding(horizontal = 16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CommunityNameSection(
                    name = communityName,
                    onUpdateName = { onAction(CreateCommunityAction.OnUpdateCommunityName(it)) },
                    isError = false, /*TODO*/
                    onChangeError = { /*TODO*/ }
                )
                CommunityDescriptionSection(
                    description = communityDescription,
                    onDescriptionChange = {
                        onAction(
                            CreateCommunityAction.OnUpdateCommunityDescription(
                                it
                            )
                        )
                    },
                )
                Spacer(modifier = Modifier.size(16.dp))
                SwitchSetting(
                    title = "Require Admin Approval",
                    description = "Require admin approval before joining",
                    isChecked = requireAdminApproval,
                    onCheckedChange = {
                        onAction(
                            CreateCommunityAction.OnRequireAdminApprovalChanged(
                                it
                            )
                        )
                    },
                )
                Spacer(modifier = Modifier.size(16.dp))
                SwitchSetting(
                    title = "Allow any email domain",
                    description = "Allow any email domain to join",
                    isChecked = allowAnyEmailDomain,
                    onCheckedChange = {
                        onAction(
                            CreateCommunityAction.OnAllowAnyEmailDomainChanged(
                                it
                            )
                        )
                    },
                )
                if (!allowAnyEmailDomain) {
                    AllowedEmailDomainsSection(
                        modifier = Modifier.weight(1f),
                        allowedEmailDomains = allowedEmailDomains,
                        onAddEmailDomain = {
                            onAction(
                                CreateCommunityAction.OnAddAllowedEmailDomain(
                                    it
                                )
                            )
                        },
                        onRemoveEmailDomain = {
                            onAction(
                                CreateCommunityAction.OnRemoveAllowedEmailDomain(
                                    it
                                )
                            )
                        },
                    )
                }
                Button(
                    onClick = { onAction(CreateCommunityAction.OnCreateCommunityClicked) },
                    modifier = Modifier.padding(32.dp).fillMaxWidth(),
                    enabled = communityName.isNotBlank()
                ) {
                    Text("Create Community")
                }
            }
        }

    }

}
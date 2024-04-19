package com.gp.socialapp.presentation.chat.addmembers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.creategroup.SelectableUser
import com.gp.socialapp.presentation.chat.creategroup.components.ChooseGroupMembersSection
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.add_selected_members

data class AddMembersScreen(
    val roomId: String,
    val groupMembersIds: List<String>,
): Screen {
    @Composable
    override fun Content() {
        val navigator= LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<AddMembersScreenModel>()
        var isInitialized by remember { mutableStateOf(false) }
        if (!isInitialized) {
            screenModel.init(roomId, groupMembersIds)
            isInitialized = true
        }
        val state by screenModel.uiState.collectAsState()
        if(state.isDone) {
            screenModel.resetState()
            navigator.pop().apply {
                isInitialized = false
            }
        }
        AddMembersContent(
            selectedUsers = state.selectedUsers,
            allUsers = state.allUsers,
            onRemoveMember = screenModel::removeMember,
            onAddMember = screenModel::addMember,
            onAddMembersClicked = {
                screenModel.submitGroupUsers()
                navigator.pop()
            }
        )
    }
    @Composable
    private fun AddMembersContent (
        modifier: Modifier = Modifier,
        selectedUsers: List<User>,
        allUsers: List<SelectableUser>,
        onRemoveMember: (String) -> Unit,
        onAddMember: (String) -> Unit,
        onAddMembersClicked: () -> Unit,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.inverseOnSurface,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                ChooseGroupMembersSection(
                    modifier = Modifier.padding(top = 8.dp),
                    selectedUsers = selectedUsers,
                    users = allUsers,
                    onUnselectUser = {
                        onRemoveMember(it)
                    },
                    onUserClick = { selectableUser ->
                        println("selectableUser: ${selectableUser}")
                        if (selectableUser.isSelected) {
                            println("Removing member")
                            onRemoveMember(selectableUser.user.id)
                        } else {
                            println("Adding member")
                            onAddMember(selectableUser.user.id)
                        }
                    })
                Spacer(modifier = Modifier.size(8.dp))
                Button(
                    onClick = onAddMembersClicked,
                    enabled = selectedUsers.isNotEmpty(),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(resource = Res.string.add_selected_members),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }

    }
}

package com.gp.socialapp.presentation.chat.creategroup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.creategroup.SelectableUser

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseGroupMembersSection(
    modifier: Modifier = Modifier,
    selectedUsers: List<User>,
    onUnselectUser: (String) -> Unit,
    users: List<SelectableUser>,
    onUserClick: (SelectableUser) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        FlowRow {
            selectedUsers.forEach { user ->
                SelectedMemberItem(
                    user = user,
                    onUnselect = onUnselectUser
                )
            }
        }
        HorizontalDivider(
            modifier = modifier.padding(top = 16.dp),
            thickness = 1.dp
        )
        LazyColumn {
            items(users.size) { index ->
                val user = users[index]
                GroupMemberItem(
                    selectableUser = user,
                    isSelected = user.isSelected,
                    isSelectable = true,
                    onUserClick = {
                        onUserClick(user.copy(isSelected = !user.isSelected))
                    })
            }
        }
    }
}
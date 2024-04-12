package com.gp.socialapp.presentation.chat.creategroup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseGroupMembersSection(
    modifier: Modifier = Modifier,
    selectedUsers: List<User>,
    onUnselectUser: (String) -> Unit,
    users: List<User>,
    onUserClick: (User) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        FlowRow {
            selectedUsers.forEach { user ->
//                SelectedMemberItem(
//                    user = user,
//                    onUnselect = onUnselectUser
//                )
            }
        }
        androidx.compose.material3.Divider(
            thickness = 1.dp, modifier = modifier.padding(top = 16.dp)
        )
        LazyColumn {
            items(users.size) { index ->
//                GroupMemberItem(
//                    user = user,
//                    isSelected = user.isSelected,
//                    isSelectable = true,
//                    onUserClick = {
//                        onUserClick(user.copy(isSelected = !user.isSelected))
//                    })
            }
        }
    }
}
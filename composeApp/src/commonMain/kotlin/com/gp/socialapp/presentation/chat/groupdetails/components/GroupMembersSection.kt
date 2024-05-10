package com.gp.socialapp.presentation.chat.groupdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.creategroup.SelectableUser
import com.gp.socialapp.presentation.chat.creategroup.components.GroupMemberItem

@Composable
fun GroupMembersSection(
    members: List<User>,
    admins: List<String>,
    isAdmin: Boolean = false,
    onAddMembersClicked: () -> Unit,
    onUserClicked: (User) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 4.dp).fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Members:", style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${members.size} Member${if (members.size != 1) "s" else ""}",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isAdmin) {
                item {
                    AddMembersSection(onAddMembersClicked)
                }
            }
            items(members.size) { index ->
                val user = members[index]
                GroupMemberItem(selectableUser = SelectableUser(user, false),
                    isAdmin = admins.any { it == user.id },
                    onUserClick = {
                        onUserClicked(it)
                    })
            }
        }
    }
}
package com.gp.socialapp.presentation.chat.groupdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.creategroup.SelectableUser
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar
import com.gp.socialapp.presentation.chat.creategroup.components.GroupMemberItem
import com.gp.socialapp.presentation.chat.groupdetails.components.imagevectors.MyIconPack
import com.gp.socialapp.presentation.chat.groupdetails.components.imagevectors.myiconpack.AddPeopleCircle

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
        modifier = modifier.background(Color.Transparent).padding(top = 4.dp).fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(Color.Transparent).fillMaxWidth()
        ) {
            Text(
                text = "Members:", style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${members.size} Member${if (members.size != 1) "s" else ""}",
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isAdmin) {
                item {
                    ListItem(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onAddMembersClicked() },
                        headlineContent = {
                            Text("Add members", fontSize = 18.sp)
                        },
                        leadingContent = {
                            Icon(
                                imageVector = MyIconPack.AddPeopleCircle,
                                contentDescription = "Add members",
                                modifier = Modifier.size(48.dp)
                            )
                        },
                    )
                }
            }
            items(members.size) { index ->
                val user = members[index]
                GroupMemberItem(
                    user = user,
                    onUserClicked = { onUserClicked(user) },
                    isAdmin = admins.contains(user.id)
                )
            }
        }
    }
}


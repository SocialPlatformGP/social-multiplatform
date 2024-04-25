package com.gp.socialapp.presentation.community.communitymembers.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.model.CommunityMemberRequest

@Composable
fun CommunityMembersList(
    modifier: Modifier = Modifier,
    members: List<User>,
    admins: List<String>,
    onUserClicked: (String) -> Unit,
) {
    LazyColumn (
        modifier = modifier
    ) {
        items(members.size) { index ->
            val user = members[index]
            CommunityMemberItem(
                user = user,
                isAdmin = admins.contains(user.id),
                onUserClicked = onUserClicked
            )
        }
    }

}
package com.gp.socialapp.presentation.community.communitymembers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.presentation.community.communitymembers.components.CommunityMemberRequestsList
import com.gp.socialapp.presentation.community.communitymembers.components.CommunityMembersList
import com.gp.socialapp.presentation.community.communitymembers.components.CommunityMembersSection

data class CommunityMembersScreen(
    val communityId: String
): Screen{
    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }
    companion object {
        @Composable
        fun CommunityMembersContent(
            modifier: Modifier = Modifier,
            requests: List<CommunityMemberRequest>,
            admins: List<String>,
            isAdmin: Boolean,
            onAction: () -> Unit,
            members: List<User>
        ) {
            Scaffold(
                modifier = modifier.padding(16.dp)
            ) { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    if (isAdmin && requests.isNotEmpty()) {
                        CommunityMembersSection(
                            title = "Requests",
                            body = {
                                CommunityMemberRequestsList(
                                    requests = requests,
                                    onAcceptRequest = { TODO() },
                                    onDeclineRequest = { TODO() },
                                    onUserClicked = { TODO() }
                                )
                            }
                        )
                    }
                    CommunityMembersSection(
                        title = "Members",
                        body = {
                            CommunityMembersList(
                                members = members,
                                admins = admins,
                                onUserClicked = {}
                            )
                        }
                    )
                }
            }
        }
    }
}

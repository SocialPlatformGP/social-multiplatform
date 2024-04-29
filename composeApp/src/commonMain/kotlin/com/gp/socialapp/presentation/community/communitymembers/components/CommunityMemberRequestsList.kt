package com.gp.socialapp.presentation.community.communitymembers.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gp.socialapp.data.community.model.CommunityMemberRequest

@Composable
fun CommunityMemberRequestsList(
    modifier: Modifier = Modifier,
    requests: List<CommunityMemberRequest>,
    onUserClicked: (String) -> Unit,
    onAcceptRequest:(String) -> Unit,
    onDeclineRequest:(String) -> Unit
) {
    LazyColumn (
        modifier = modifier
    ) {
        items(requests.size) { index ->
            val request = requests[index]
            CommunityMemberRequestItem(
                request = request,
                onUserClicked = onUserClicked,
                onAcceptRequest = onAcceptRequest,
                onDeclineRequest = onDeclineRequest
            )
        }
    }

}
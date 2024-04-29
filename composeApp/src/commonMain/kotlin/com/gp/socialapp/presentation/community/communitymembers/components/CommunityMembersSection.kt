package com.gp.socialapp.presentation.community.communitymembers.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.community.model.CommunityMemberRequest

@Composable
fun CommunityMembersSection(
    modifier: Modifier = Modifier,
    title: String,
    body: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider(Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
        body()
    }

}
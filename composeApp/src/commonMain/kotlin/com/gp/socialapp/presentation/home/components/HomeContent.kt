package com.gp.socialapp.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.presentation.home.screen.HomeUiAction

@Composable
fun HomeContent(
    modifier: Modifier,
    windowWidthSizeClass: WindowWidthSizeClass,
    communities: List<Community>,
    action: (HomeUiAction) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Your communities",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        when(windowWidthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                CompactCommunityList(
                    modifier = Modifier.fillMaxWidth(),
                    communities = communities,
                    onCommunityClicked = { communityId ->
                        action(HomeUiAction.OnCommunityClicked(communityId))
                    },
                    onOptionsMenuClicked = { community ->
                        action(HomeUiAction.OnOptionsMenuClicked(community))
                    }
                )
            }

            else -> {
                GridCommunityList(
                    modifier = Modifier.fillMaxWidth(),
                    communities = communities,
                    onCommunityClicked = { communityId ->
                        action(HomeUiAction.OnCommunityClicked(communityId))
                    },
                    onOptionsMenuClicked = { community ->
                        action(HomeUiAction.OnOptionsMenuClicked(community))
                    },
                    onCommunityMaterialClicked = { communityId ->
                        action(HomeUiAction.OnCommunityMaterialClicked(communityId))
                    },
                    onCommunityMembersClicked = { communityId ->
                        action(HomeUiAction.OnCommunityMembersClicked(communityId))
                    }
                )
            }
        }
    }
}
package com.gp.socialapp.presentation.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.community.source.remote.model.Community

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridCommunityList(
    modifier: Modifier = Modifier,
    communities: List<Community>,
    onCommunityClicked: (String) -> Unit,
    onOptionsMenuClicked: (Community) -> Unit,
    onCommunityMaterialClicked: (String) -> Unit,
    onCommunityMembersClicked: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        modifier = modifier.fillMaxWidth(),
        userScrollEnabled = true,
    ) {
        items(communities) { community ->
            val animatable = remember { Animatable(0.7f) }
            LaunchedEffect(key1 = true) {
                animatable.animateTo(
                    1f, spring(Spring.DampingRatioNoBouncy, Spring.StiffnessVeryLow)
                )
            }
            CommunityGridItem(
                modifier = Modifier.graphicsLayer(scaleY = animatable.value, scaleX = animatable.value),
                community = community,
                onCommunityClicked = onCommunityClicked,
                onOptionsMenuClicked = onOptionsMenuClicked,
                onCommunityMaterialClicked = onCommunityMaterialClicked,
                onCommunityMembersClicked = onCommunityMembersClicked
            )
        }
    }
}

@Composable
fun CommunityGridItem(
    modifier: Modifier = Modifier,
    community: Community,
    onCommunityClicked: (String) -> Unit,
    onOptionsMenuClicked: (Community) -> Unit,
    onCommunityMaterialClicked: (String) -> Unit,
    onCommunityMembersClicked: (String) -> Unit,
) {
    Card(
        onClick = {
            onCommunityClicked(community.id)
        }, modifier = modifier.padding(8.dp).width(300.dp), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth().focusable(enabled = false)
        ) {
            //Top Row (Title + option button)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = community.name,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        onOptionsMenuClicked(community)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options Menu"
                    )
                }
            }
            //Secondary Row (Description + members count)
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if(community.description.isNotBlank()){
                    Text(
                        text = community.description,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = Modifier.weight(2.5f),
                    )
                }
                val count = community.members.count()
                Text(
                    text = "$count ${if (count == 1) "Member" else "Members"}",
                    modifier = Modifier.weight(1f),
                    textAlign = if(community.description.isNotBlank()) TextAlign.End else TextAlign.Start,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            //divider
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            //Bottom Row (Navigate to material, members, assignments)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        onCommunityMembersClicked(community.id)
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Groups,
                        contentDescription = "Community Members"
                    )
                }
                IconButton(
                    onClick = {
                        onCommunityMaterialClicked(community.id)
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Folder,
                        contentDescription = "Community Material"
                    )
                }
            }
        }
    }
}
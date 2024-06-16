package com.gp.socialapp.presentation.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.community.source.remote.model.Community

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompactCommunityList(
    modifier: Modifier = Modifier,
    communities: List<Community>,
    onCommunityClicked: (String) -> Unit,
    onOptionsMenuClicked: (Community) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        userScrollEnabled = true,
    ) {
        items(communities) { community ->
            val animatable = remember { Animatable(0.7f) }
            LaunchedEffect(key1 = true) {
                animatable.animateTo(
                    1f, spring(Spring.DampingRatioNoBouncy, Spring.StiffnessVeryLow)
                )
            }
            CommunityListItem(
                modifier = Modifier.fillMaxWidth().graphicsLayer {
                    this.scaleX = animatable.value
                    this.scaleY = animatable.value
                }.combinedClickable(
                    onClick = {
                        onCommunityClicked(community.id)
                    },
                    onLongClick = {
                        onOptionsMenuClicked(community)
                    }
                ),
                community = community,
                onCommunityClicked = { onCommunityClicked(community.id) },
                onOptionsMenuClicked = { onOptionsMenuClicked(community) }
            )
        }
    }
}

@Composable
fun CommunityListItem(
    modifier: Modifier = Modifier,
    community: Community,
    onCommunityClicked: (String) -> Unit,
    onOptionsMenuClicked: (Community) -> Unit,
) {
    Card(
        onClick = {
            onCommunityClicked(community.id)
        }, modifier = modifier.padding(8.dp).fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.padding(16.dp).size(64.dp).clip(RoundedCornerShape(8.dp))
                    .background(getCommunityColor(community.name))
            ) {
                Text(
                    text = if (community.name.isNotBlank()) community.name.substring(0, 2)
                        .uppercase() else "CO".uppercase(),
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                modifier = Modifier.padding(vertical = 16.dp).padding(end = 16.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = community.name,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(community.description.isNotBlank()){
                        Text(
                            text = community.description,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.weight(2.5f),
                        )
                    }
                    val count = community.members.count()
                    Text(
                        text = "$count ${if (count == 1) "Member" else "Members"}",
                        modifier = Modifier.weight(1f),
                        textAlign = if(community.description.isNotBlank()) TextAlign.End else TextAlign.Start,
                        maxLines = 1,
                        color = Color.White.copy(alpha = 0.7f),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

    }
}

private fun getCommunityColor(name: String): Color {
    val index = name.hashCode() % 5
    return when (index) {
        0 -> Color.Red.copy(alpha = 0.5f)
        1 -> Color.Blue.copy(alpha = 0.5f)
        2 -> Color.Green.copy(alpha = 0.5f)
        3 -> Color.Yellow.copy(alpha = 0.5f)
        4 -> Color.Magenta.copy(alpha = 0.5f)
        else -> Color.Gray.copy(alpha = 0.5f)
    }
}
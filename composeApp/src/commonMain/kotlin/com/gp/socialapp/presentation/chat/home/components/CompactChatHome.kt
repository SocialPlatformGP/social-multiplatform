package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.presentation.chat.home.ChatHomeUiEvent
import com.gp.socialapp.presentation.chat.home.ChatHomeUiState

@Composable
fun CompactChatHome(
    modifier: Modifier = Modifier,
    recentRooms: List<RecentRoom>,
    currentUserId: String,
    event: (ChatHomeUiEvent) -> Unit,
    ){
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { event(ChatHomeUiEvent.OnCreateChatClick) }) {
            Icon(
                imageVector = Icons.Default.AddComment,
                contentDescription = null
            )
        }
    },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(recentRooms) { recentRoom ->
                    val animatable = remember { Animatable(0.5f) }
                    LaunchedEffect(key1 = true) {
                        animatable.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
                    }
                    RecentRoomItem(
                        modifier = Modifier.graphicsLayer {
                            this.scaleX = animatable.value
                            this.scaleY = animatable.value
                        },
                        recentRoom = recentRoom,
                        currentUserId = currentUserId,
                        event = event
                    )
                }
            }
        }
    }
}
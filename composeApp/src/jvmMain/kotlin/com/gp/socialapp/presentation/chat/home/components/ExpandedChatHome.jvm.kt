package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.ScaleTransition
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreen
import com.gp.socialapp.presentation.chat.home.ChatHomeUiEvent
import com.gp.socialapp.presentation.chat.home.ChatHomeUiState
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
actual fun ExpandedChatHome(
    modifier: Modifier,
    startingChatRecentRoom: RecentRoom,
    recentRooms: List<RecentRoom>,
    currentUserId: String,
    event: (ChatHomeUiEvent) -> Unit
) {
    val splitterState = rememberSplitPaneState(moveEnabled = false)
    var selectedRoomId by remember { mutableStateOf(startingChatRecentRoom.roomId) }
    val scope = rememberCoroutineScope()
    var roomTitle by remember { mutableStateOf("") }
    var roomPic by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) }
    if (startingChatRecentRoom.roomId != 0L) {
        with(startingChatRecentRoom) {
            if (this.isPrivate && this.roomId != selectedRoomId) {
                selectedRoomId = this.roomId
                roomTitle =
                    if (this.senderId == currentUserId) this.receiverName else this.senderName
                roomPic =
                    if (this.senderId == currentUserId) this.receiverPicUrl else this.senderPicUrl
                isPrivate = true
            } else if (this.roomId != selectedRoomId) {
                selectedRoomId = this.roomId
                roomTitle = this.senderName
                roomPic = this.senderPicUrl
                isPrivate = false
            }

        }
    }
    val chatRoomScreen by remember(selectedRoomId, roomTitle, roomPic, isPrivate) {
        mutableStateOf(
            ChatRoomScreen(
                roomId = selectedRoomId,
                roomTitle = roomTitle,
                roomAvatarUrl = roomPic,
                isPrivate = isPrivate
            )
        )
    }
    Scaffold { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            val boxWithConstraintsScope = this
            val maxWidth = boxWithConstraintsScope.maxWidth
            Row(modifier = Modifier.fillMaxSize()) {
                HorizontalSplitPane(
                    splitPaneState = splitterState
                ) {
                    first(maxWidth * 0.4f) {
                        Surface(
                            tonalElevation = 8.dp, modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().background(
                                    color = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shadowElevation = 8.dp
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Chats",
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(8.dp)
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            IconButton(onClick = {
                                                event(ChatHomeUiEvent.OnCreateChatClick)
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.AddComment,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                        HorizontalDivider(thickness = 1.dp)
                                    }
                                }
                                LazyColumn(
                                    modifier = Modifier.fillMaxWidth().weight(1f)
                                ) {
                                    items(recentRooms) { recentRoom ->
                                        val animatable = remember { Animatable(0.5f) }
                                        LaunchedEffect(key1 = true) {
                                            animatable.animateTo(
                                                1f, tween(350, easing = FastOutSlowInEasing)
                                            )
                                        }
                                        Row {
                                            RecentRoomItem(modifier = Modifier.graphicsLayer {
                                                this.scaleX = animatable.value
                                                this.scaleY = animatable.value
                                            },
                                                recentRoom = recentRoom,
                                                isSelected = recentRoom.roomId == selectedRoomId,
                                                currentUserId = currentUserId,
                                                event = { event ->
                                                    when (event) {
                                                        is ChatHomeUiEvent.OnRecentChatClick -> {
                                                            event.recentRoom.let {
                                                                if (it.isPrivate && it.roomId != selectedRoomId) {
                                                                    selectedRoomId = it.roomId
                                                                    roomTitle =
                                                                        if (it.senderId == currentUserId) it.receiverName else it.senderName
                                                                    roomPic =
                                                                        if (it.senderId == currentUserId) it.receiverPicUrl else it.senderPicUrl
                                                                    isPrivate = true
                                                                } else if (it.roomId != selectedRoomId) {
                                                                    selectedRoomId = it.roomId
                                                                    roomTitle = it.senderName
                                                                    roomPic = it.senderPicUrl
                                                                    isPrivate = false
                                                                }
                                                            }
                                                        }

                                                        else -> event(event)
                                                    }
                                                })
                                        }
                                    }
                                }
                            }
                        }
                    }
                    second(maxWidth * 0.6f) {
                        Row(Modifier.fillMaxSize()) {
                            VerticalDivider(
                                Modifier.fillMaxHeight(),
                                thickness = 2.dp,
                                color = DividerDefaults.color.copy(alpha = 0.5f)
                            )
                            if (selectedRoomId == 0L) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        text = "Select a chat to view",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(16.dp).align(Alignment.Center)
                                    )
                                }
                            } else {
                                println("recomposition triggered inside else $chatRoomScreen")
                                Navigator(
                                    screen = chatRoomScreen,
                                ){
                                    ScaleTransition(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

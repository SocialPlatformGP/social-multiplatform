package com.gp.socialapp.presentation.userprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.post.feed.components.FeedPostItem
import com.seiko.imageloader.rememberImagePainter

data class UserProfileScreen(val userId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<UserProfileScreenModel>()
        LifecycleEffect(
            onStarted = { screenModel.init(userId) },
            onDisposed = { screenModel.onDispose() }
        )
        val state by screenModel.uiState.collectAsState()
        UserProfileContent(
            state = state,
            action = {
                when (it) {
                    UserProfileUiAction.OnNavigateUpClicked -> navigator.pop()
                    is UserProfileUiAction.OnSendMsgClicked -> {
                        //todo navigate to chat screen
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileContent(
    state: UserProfileUiState = UserProfileUiState(),
    action: (UserProfileUiAction) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    //todo add title or search
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            action(UserProfileUiAction.OnNavigateUpClicked)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Arrow Back"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            Modifier.padding(it).padding(horizontal = 8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(state.user.profilePictureURL.ifBlank { "https://images.pexels.com/photos/1222271/pexels-photo-1222271.jpeg" }),
                            contentDescription = "background Image",
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White) //delete this line
                                .height(100.dp)
                                .blur(10.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.align(Alignment.CenterEnd)

                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Notifications,
                                    contentDescription = "follow user Acticity Icon"
                                )
                            }
                        }
                    }
                    Image(
                        painter = rememberImagePainter(state.user.profilePictureURL.ifBlank { "https://images.pexels.com/photos/1222271/pexels-photo-1222271.jpeg" }),
                        contentDescription = "User Image",
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(top = 24.dp)
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(4.dp, MaterialTheme.colorScheme.primaryContainer, CircleShape)
                            .align(Alignment.CenterStart)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.user.firstName + " " + state.user.lastName, //replace with user name
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { action(UserProfileUiAction.OnSendMsgClicked(state.user.id)) },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Send,
                            contentDescription = "follow user Acticity Icon"
                        )
                    }
                }

                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = state.user.bio,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.user.email,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
            Spacer(modifier = Modifier.size(4.dp))
            TabRow(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),

                selectedTabIndex = 0,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[0])
                            .padding(horizontal = 50.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally),
                        height = 8.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ) {
                Tab(
                    selected = true,
                    onClick = {},
                    text = {
                        Text(
                            text = "Posts",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp
                        )
                    },
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.posts) {
                    FeedPostItem(post = it, {}, "")
                }
            }
        }
    }

}


package com.gp.socialapp.presentation.post.feed

import EditPostScreen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.presentation.post.create.CreatePostScreen
import com.gp.socialapp.presentation.post.feed.components.FeedPostItem
import com.gp.socialapp.presentation.post.feed.components.FeedTopBar
import com.gp.socialapp.presentation.post.feed.components.FilesBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object FeedScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        println("FeedScreen")
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<FeedScreenModel>()
        var currentAttachments by remember { mutableStateOf(emptyList<PostFile>()) }
        val scope = rememberCoroutineScope()
        val state by screenModel.state.collectAsState()
        var isFileBottomSheetOpen by remember { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState()
        val tabItems = listOf(
            TabItem("General", Icons.Filled.AllInclusive),
            TabItem("Spotlight", Icons.Filled.NotificationImportant),
        )
        screenModel.getAllPosts()
        FeedContent(
            state = state,
            currentUserID = "",
            onPostEvent = { action ->
                when (action) {
                    is PostEvent.OnAddPost -> {
                        navigator.push(CreatePostScreen)
                    }

                    is PostEvent.OnPostClicked -> {
                        //todo navigate to post details
                    }

                    is PostEvent.OnPostEdited -> {
                        navigator.push(EditPostScreen(action.post))
                    }

                    is PostEvent.OnTagClicked -> {
                        //todo navigate to search by tag
                    }

                    is PostEvent.OnAudioClicked -> {
                        //todo navigate to audio player
                    }

                    is PostEvent.OnDocumentClicked -> {
                        //todo navigate to document viewer
                    }

                    is PostEvent.OnImageClicked -> {
                        //todo navigate to image viewer
                    }

                    is PostEvent.OnVideoClicked -> {
                        //todo navigate to video player
                    }

                    is PostEvent.OnPostUpVoted -> {
                        screenModel.upVote(action.post)
                    }

                    is PostEvent.OnPostDownVoted -> {
                        screenModel.downVote(action.post)
                    }

                    is PostEvent.OnPostDeleted -> {
                        screenModel.deletePost(action.post)
                    }

                    is PostEvent.OnViewFilesAttachmentClicked -> {
                        currentAttachments = action.files
                        scope.launch {
                            isFileBottomSheetOpen = true
                        }
                    }
                    is PostEvent.OnPostReported -> {
                        TODO()
                    }
                    is PostEvent.OnPostShareClicked -> {
                        TODO()
                    }
                    else -> {}
                }
            },
            onNavigationAction = { action ->
                when (action) {
                    is NavigationAction.NavigateToSearch -> {
                        //todo navigate to search
                    }

                    is NavigationAction.NavigateToPostDetails -> {
                        //todo navigate to post details
                    }

                    else -> {}
                }
            },
            scope = scope,
            currentAttachments = currentAttachments,
            isFileBottomSheetOpen = isFileBottomSheetOpen,
            tabItems = tabItems,
            onDismissBottomSheet = {
                isFileBottomSheetOpen = false
            },
            onShowBottomSheet = {
                isFileBottomSheetOpen = true
            },
            bottomSheetState = bottomSheetState,
        )
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun FeedContent(
        modifier: Modifier = Modifier,
        onPostEvent: (PostEvent) -> Unit,
        onNavigationAction: (NavigationAction) -> Unit,
        currentUserID: String,
        state: FeedUiState,
        scope: CoroutineScope = rememberCoroutineScope(),
        currentAttachments: List<PostFile>,
        isFileBottomSheetOpen: Boolean,
        tabItems: List<TabItem>,
        onDismissBottomSheet: () -> Unit = { },
        onShowBottomSheet: () -> Unit = { },
        bottomSheetState: SheetState,
    ) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val pagerState = rememberPagerState { tabItems.size }
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onPostEvent(PostEvent.OnAddPost) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            },
            topBar = {
                FeedTopBar(onNavigationAction)
            }
        ) { paddingValues ->
            if (state.error !is FeedError.NoError) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = (state.error as FeedError.NetworkError).message,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                TabRow(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth().clip(
                            RoundedCornerShape(
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        ),
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .padding(horizontal = 50.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally),
                            height = 8.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ) {
                    tabItems.forEachIndexed { index, tabItem ->
                        Tab(
                            selected = (index == selectedTabIndex),
                            onClick = {
                                selectedTabIndex = index
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = tabItem.title,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 16.sp
                                )
                            },
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                ) { index ->
                    when (index) {
                        0 -> {
                            FeedPosts(
                                posts = state.posts,
                                onPostEvent = onPostEvent,
                                currentUserID = currentUserID
                            )
                        }

                        1 -> {
                            FeedPosts(
                                posts = state.posts.filter { it.type == "vip" },
                                onPostEvent = onPostEvent,
                                currentUserID = currentUserID
                            )
                        }
                    }
                }
                if (isFileBottomSheetOpen) {
                    FilesBottomSheet(
                        attachments = currentAttachments,
                        onDismiss = onDismissBottomSheet,
                        onPostEvent = onPostEvent,
                        state = bottomSheetState,
                    )
                }
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if (!pagerState.isScrollInProgress) {
                    selectedTabIndex = pagerState.currentPage
                }
            }
        }
    }

    @Composable
    fun FeedPosts(
        posts: List<Post>,
        onPostEvent: (PostEvent) -> Unit,
        currentUserID: String
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(posts) { post ->
                    FeedPostItem(
                        post = post,
                        onPostEvent = onPostEvent,
                        currentUserID = currentUserID
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                }
            }
        }
    }
}

data class TabItem(val title: String, val imageVector: ImageVector)

package com.gp.socialapp.presentation.post.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.presentation.post.postDetails.PostDetailsScreen
import com.gp.socialapp.presentation.post.search.components.SearchResultHeader
import com.gp.socialapp.presentation.post.search.components.SearchResultList

data class SearchResultScreen(
    val searchTerm: String,
    val isTag: Boolean = false
): Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<SearchResultScreenModel>()
        val state by screenModel.uiState.collectAsState()
        SearchResultContent(
            posts = state.posts,
            onPostClicked = { navigator.push(PostDetailsScreen(it)) }
        )
    }

    @Composable
    fun SearchResultContent(
        modifier: Modifier = Modifier,
        posts: List<Post>,
        onPostClicked: (Post) -> Unit,
        ) {
        Scaffold (
            modifier = modifier.fillMaxSize()
        ){
            Column  (
                modifier = Modifier.fillMaxSize()
            ){
                SearchResultHeader(
                    searchTerm = searchTerm,
                    isTag = isTag
                )
                SearchResultList(
                    posts = posts,
                    onPostClicked = onPostClicked
                )
            }
        }
    }
}

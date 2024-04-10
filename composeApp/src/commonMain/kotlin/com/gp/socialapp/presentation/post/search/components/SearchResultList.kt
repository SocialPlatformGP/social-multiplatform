package com.gp.socialapp.presentation.post.search.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gp.socialapp.data.post.source.remote.model.Post

@Composable
fun SearchResultList(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onPostClicked: (Post) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()){
        items(posts.size) { index ->
            SearchResultItem(
                item = posts[index],
                onPostClicked = onPostClicked
            )
        }
    }
}
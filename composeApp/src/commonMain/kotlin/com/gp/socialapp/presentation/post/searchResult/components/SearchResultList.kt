package com.gp.socialapp.presentation.post.searchResult.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.Post

@Composable
fun SearchResultList(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onPostClicked: (Post) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()){
        items(posts.size) { index ->
            SearchResultItem(
                item = posts[index],
                onPostClicked = onPostClicked
            )
        }
    }
}
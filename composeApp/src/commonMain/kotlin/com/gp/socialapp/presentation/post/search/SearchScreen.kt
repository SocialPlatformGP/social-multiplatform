package com.gp.socialapp.presentation.post.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.post.search.components.RecentSearchesSection
import com.gp.socialapp.presentation.post.searchResult.SearchResultScreen

object SearchScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<SearchScreenModel>()
        val isScreenModelInitialized by remember { mutableStateOf(false) }
        if (!isScreenModelInitialized) {
            screenModel.initScreenModel()
        }
        val state by screenModel.uiState.collectAsState()
        SearchScreenContent(
            recentSearches = state.recentSearches,
            onSearchItemClick = {
                screenModel.addRecentSearchItem(it)
                navigator.push(SearchResultScreen(it, false))
            },
            onDeleteRecentItem = screenModel::deleteRecentSearchItem,
            onSearchQueryChanged = screenModel::onSearchQueryChanged,
            suggestionItems = state.suggestionItems,
            onBackPressed = navigator::pop
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SearchScreenContent(
        modifier: Modifier = Modifier,
        recentSearches: List<String>,
        onSearchItemClick: (String) -> Unit,
        onDeleteRecentItem: (String) -> Unit,
        onSearchQueryChanged: (String) -> Unit,
        suggestionItems: List<String>,
        onBackPressed: () -> Unit
    ) {
        var searchQuery by remember { mutableStateOf("") }
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    IconButton(
                        onClick = onBackPressed
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    TextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            onSearchQueryChanged(it)
                        },
                        label = { Text("Search Query") },
                        leadingIcon = { Icons.Default.Search },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(
                                    onClick = { searchQuery = "" }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear"
                                    )
                                }
                            }
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            onSearchItemClick(searchQuery)
                        }),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp)
            ) {
                if (searchQuery.isBlank()) {
                    RecentSearchesSection(
                        items = recentSearches,
                        onItemClick = onSearchItemClick,
                        onDeleteItem = onDeleteRecentItem
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(suggestionItems.size) {
                            Text(
                                text = suggestionItems[it],
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    top = 4.dp,
                                    end = 8.dp,
                                    bottom = 4.dp
                                ).clickable { onSearchItemClick(suggestionItems[it]) }
                            )
                        }
                    }
                }
            }
        }

    }
}
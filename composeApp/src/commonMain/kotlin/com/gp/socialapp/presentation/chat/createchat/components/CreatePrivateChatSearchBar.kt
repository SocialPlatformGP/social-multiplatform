package com.gp.socialapp.presentation.chat.createchat.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun CreatePrivateChatSearchBar(
    modifier: Modifier = Modifier,
    onSearchQueryChange: (String) -> Unit,
    onSearchQuerySubmit: () -> Unit,
    onBackPressed: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp)
    ) {
        TextField(
            shape = RoundedCornerShape(32.dp),
            value = searchQuery,
            placeholder = { Text("Search...") },
            leadingIcon = {
                IconButton(onClick = { onBackPressed() }){
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Search",
                    )
                }
            },
            onValueChange = {
                searchQuery = it
                onSearchQueryChange(it)
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { onSearchQuerySubmit() }),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )
    }
}
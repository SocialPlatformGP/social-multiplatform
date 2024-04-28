package com.gp.socialapp.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import com.gp.socialapp.presentation.chat.home.ChatHomeUiEvent
import com.gp.socialapp.presentation.chat.home.components.RectangleFab
import com.gp.socialapp.presentation.chat.home.components.SingleFab
import com.gp.socialapp.presentation.home.HomeUiAction

@Composable
fun HomeFab(
    fabState: MutableState<Boolean>,
    onCreateCommunityClicked: () -> Unit,
    onJoinCommunityClicked: () -> Unit
) {
//    FloatingActionButton(
//        onClick = onFabClicked
//
//    ) {
//        Icon(
//            imageVector = Icons.Default.Add, contentDescription = "Add"
//        )
//    }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        RectangleFab(
            fabState,
            { onCreateCommunityClicked() },
            "Create Community"
        )
        RectangleFab(
            fabState,
            { onJoinCommunityClicked() },
            "Join Community"
        )
        SingleFab(
            fabState,
            Icons.Default.Cancel
        )
    }
}
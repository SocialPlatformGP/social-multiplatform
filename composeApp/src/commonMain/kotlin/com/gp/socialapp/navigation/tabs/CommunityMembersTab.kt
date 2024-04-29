package com.gp.socialapp.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.gp.socialapp.presentation.community.communitymembers.CommunityMembersScreen

data class CommunityMembersTab(val communityId: String) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Members"
            val icon = rememberVectorPainter(Icons.Rounded.Groups)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(CommunityMembersScreen(communityId))
    }
}
package com.gp.socialapp.presentation.chat.groupdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gp.socialapp.data.auth.source.remote.model.User

@Composable
fun UserClickedDialog(
    modifier: Modifier = Modifier,
    isAdmin: Boolean,
    isCurrentUser: Boolean,
    userId: String,
    onDismiss: () -> Unit,
    onRemoveMember: (String) -> Unit,
    onMessageUser: (String) -> Unit,
    onViewProfile: (String) -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = modifier.fillMaxWidth().heightIn(min = 100.dp, max = 225.dp).padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    Text(text = "View Profile",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.weight(1F).padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxSize().clickable {
                                onViewProfile(userId)
                            })
                }
                item {
                    Text(text = "Message",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.weight(1F).padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxSize().clickable {
                                onMessageUser(userId)
                            })
                }
                if (isAdmin && !isCurrentUser) {
                    item {
                        Text(text = "Remove from Group",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1F)
                                .padding(horizontal = 16.dp, vertical = 8.dp).fillMaxSize()
                                .clickable {
                                    onRemoveMember(userId)
                                })
                    }
                }
            }
        }
    }

}
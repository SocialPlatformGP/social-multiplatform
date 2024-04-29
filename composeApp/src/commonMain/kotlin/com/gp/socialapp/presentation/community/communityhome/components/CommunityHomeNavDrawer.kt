package com.gp.socialapp.presentation.community.communityhome.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import com.seiko.imageloader.ui.AutoSizeImage

@Composable
fun CommunityHomeNavDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    user: User,
    communityId: String,
    communities: List<Community>,
    onNavigateToHome: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(8.dp).fillMaxSize()
                ) {
                    if (user.profilePictureURL.isNotBlank())

                        AutoSizeImage(
                            url = user.profilePictureURL,
                            contentDescription = "user image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                                .size(64.dp).clip(CircleShape)
                        )
                    else
                        Box(
                            modifier = Modifier.padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                                .size(64.dp).clip(CircleShape)
                                .background(Color.Red)
                        ) {
                            Text(
                                text = if (user.firstName.isNotBlank()) user.firstName[0].toString()
                                    .uppercase() + user.lastName[0].toString()
                                    .uppercase() else "Unknown",
                                fontSize = 24.sp,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        user.firstName + " " + user.lastName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        user.email,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Home") },
                        selected = false,
                        onClick = {
                            onNavigateToHome()
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    communities.forEach {
                        NavigationDrawerItem(
                            label = { Text(text = it.name) },
                            selected = it.id == communityId,
                            onClick = {
                                /*Do Nothing*/
                            }
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedButton(
                            onClick = {
                                onLogout()
                            },
                            modifier = Modifier.weight(1f).padding(8.dp)
                        ) {
                            Text(
                                text = "Logout",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        Button(
                            onClick = {
                                onNavigateToSettings()
                            },
                            modifier = Modifier.weight(1f).padding(8.dp)

                        ) {
                            Text(
                                text = "Settings",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        })
    {
        content()
    }
}
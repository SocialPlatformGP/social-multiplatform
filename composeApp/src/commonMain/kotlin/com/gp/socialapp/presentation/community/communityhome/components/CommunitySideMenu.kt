package com.gp.socialapp.presentation.community.communityhome.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.navigation.util.SideMenuNavigationItem
import com.gp.socialapp.presentation.home.components.SideMenuState
import com.gp.socialapp.util.disableClickAndRipple
import com.seiko.imageloader.ui.AutoSizeImage
import compose.icons.TablerIcons
import compose.icons.tablericons.Home
import compose.icons.tablericons.Logout
import compose.icons.tablericons.Settings

@Composable
fun CommunitySideMenu(
    modifier: Modifier = Modifier,
    user: User,

    onNavigateToHome: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
    menuTabs: List<Tab> = emptyList(),
    isDesktop: Boolean = false,
    backgroundColor: Color = Color.Transparent
) {
    var menuState by remember(windowWidthSizeClass) { mutableStateOf(SideMenuState.Expanded) }
    val menuWidthAnimation by animateDpAsState(
        if (menuState == SideMenuState.Expanded) 300.dp else 60.dp,
        animationSpec = tween(durationMillis = 700)
    )
    Row (
        modifier = Modifier.fillMaxHeight()
    ){
        Column(
            modifier = modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .widthIn(max = menuWidthAnimation)
                .disableClickAndRipple().background(backgroundColor)
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
                        text = if(user.name.isNotBlank()) user.name[0].toString() else "u"
                            .uppercase(),
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                user.name,
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
                icon = {Icon(imageVector = TablerIcons.Home, contentDescription = "Home")},
                onClick = {
                    onNavigateToHome()
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            menuTabs.forEach {
                SideMenuNavigationItem(it)
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
                    Icon(
                        imageVector = TablerIcons.Logout,
                        contentDescription = "Logout",
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Logout",
                    )
                }
                Button(
                    onClick = {
                        onNavigateToSettings()
                    },
                    modifier = Modifier.padding(8.dp)

                ) {
                    Icon(
                        imageVector = TablerIcons.Settings,
                        contentDescription = "Settings",
                    )
                }
            }
        }
        if(isDesktop){
            VerticalDivider(
                modifier = Modifier.width(1.dp)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            )
        }
    }
}
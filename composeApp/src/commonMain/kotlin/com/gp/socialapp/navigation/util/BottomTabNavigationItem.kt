package com.gp.socialapp.navigation.util

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RowScope.BottomTabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val windowSize = calculateWindowSizeClass()
    var compact: Boolean by remember {
        mutableStateOf(false)
    }
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            compact = true
        }

        else -> {
            compact = false
        }
    }
    NavigationBarItem(
        selected = tabNavigator.current.options.index == tab.options.index,
        alwaysShowLabel = false,
        onClick = { tabNavigator.current = tab },
        label = { if(compact) else Text(tab.options.title) },
        icon = {
            tab.options.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription =
                    tab.options.title
                )
            }
        }
    )
}
@Composable
fun ColumnScope.SideMenuNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    NavigationDrawerItem(
        selected = tabNavigator.current.options.index == tab.options.index,
        onClick = { tabNavigator.current = tab },
        label = { Text(tab.options.title) },
        icon = {
            tab.options.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription =
                    tab.options.title
                )
            }
        }
    )
}

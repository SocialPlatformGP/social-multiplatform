package com.gp.socialapp.presentation.app

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.gp.socialapp.di.appModules
import com.gp.socialapp.presentation.auth.login.LoginScreen
import com.gp.socialapp.presentation.auth.signup.SignUpScreen

import com.gp.socialapp.theme.AppTheme
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext

@Composable
internal fun App() {
    KoinApplication(application = ({
        modules(appModules)
    })) {
        AppTheme {
//            TabNavigator(PostsTab) {
//                Scaffold(
//                    content = {
//                        CurrentTab()
//                    },
//                    bottomBar = {
//                        NavigationBar {
//                            TabNavigationItem(tab = PostsTab)
//                            TabNavigationItem(tab = ChatTab)
//                            TabNavigationItem(tab = MaterialTab)
//                        }
//                    }
//                )
//            }
            Navigator(
                LoginScreen
            )
        }
    }
}
@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
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

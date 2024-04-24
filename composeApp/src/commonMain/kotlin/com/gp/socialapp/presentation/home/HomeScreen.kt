package com.gp.socialapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import kotlinx.coroutines.launch

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<HomeScreenModel>()
        LifecycleEffect(onStarted = { screenModel.init() })
        val state = screenModel.uiState.collectAsState()
        HomeScreenContent(state = state.value, action = {
            //todo handle actions
        })
    }
}

@Composable
fun HomeScreenContent(
    state: HomeUiState, action: (HomeUiAction) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { /* Drawer content */ }
        },
    ) {
        Scaffold(topBar = {
            HomeTopBar(
                user = state.user, action = action
            )
        }) { padding ->
            HomeContent(
                modifier = Modifier.padding(padding),
                communities = state.communities,
                action = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            )
        }

    }
}

@Composable
fun HomeContent(
    communities: List<Community>, action: () -> Unit, modifier: Modifier
) {
//        ModalNavigationDrawer(drawerContent = {
//            ModalDrawerSheet {
//                Text("Drawer title", modifier = Modifier.padding(16.dp))
//                HorizontalDivider()
//                NavigationDrawerItem(label = { Text(text = "Drawer Item") },
//                    selected = false,
//                    onClick = { /*TODO*/ })
//                LazyColumn {
//                    items(communities) { community ->
//                        Text(text = community.name)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//                }
//            }
//        }) {
//            // Screen content
//        }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(communities) { community ->
                Text(text = community.name,
                    modifier = Modifier.clickable { action() }
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    user: User, action: (HomeUiAction) -> Unit
) {
    TopAppBar(title = {
        Text(
            text = "Hello, ${user.firstName}",
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }, navigationIcon = {
        IconButton(onClick = {
            //todo handle navigation
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout"
            )
        }

    })
}

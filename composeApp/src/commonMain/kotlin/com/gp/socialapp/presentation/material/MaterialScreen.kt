package com.gp.socialapp.presentation.material

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.material.components.MaterialScreenContent
import com.gp.socialapp.util.copyToClipboard
import com.mohamedrejeb.calf.core.LocalPlatformContext

data class MaterialScreen(
    val communityId: String,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<MaterialScreenModel>()
        val context = LocalPlatformContext.current
        LifecycleEffect(
            onStarted = {
                screenModel.init(communityId)
            },
            onDisposed = {}
        )
        val state by screenModel.uiState.collectAsState()
        MaterialScreenContent(
            state = state,
            action = {
                when (it) {
                    is MaterialAction.OnBackClicked -> {
                        if (state.listOfPreviousFolder.isNotEmpty())
                            screenModel.closeFolder()
                        else
                            navigator.pop()
                    }
                    is MaterialAction.OnCopyLinkClicked -> {
                        context.copyToClipboard(it.url)
                    }
                    else -> {
                        screenModel.handleUiEvent(it)
                    }
                }
            },
        )

    }

}


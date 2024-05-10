package com.gp.socialapp.presentation.chat.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.chat.repository.RecentRoomRepository
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatHomeScreenModel(
    private val recentRoomRepository: RecentRoomRepository,
    private val authenticationRepository: AuthenticationRepository
) : ScreenModel {
    private val state = MutableStateFlow(ChatHomeUiState())
    val uiState = state.asStateFlow()


    fun init() {
        getCurrentUser()
    }


    fun getCurrentUser() {
        screenModelScope.launch(DispatcherIO) {
            authenticationRepository.getSignedInUser().let { result ->
                when (result) {
                    is Result.SuccessWithData -> {
                        state.update {
                            it.copy(currentUser = result.data)
                        }
                        fetchRecentRooms(result.data.id)
                    }

                    is Result.Error -> {
                        // TODO Handle error
                        Napier.d("Error: ${result.message}")
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun fetchRecentRooms(id: String) {
        screenModelScope.launch {
            recentRoomRepository.fetchRecentRooms(id, this).collectLatest { result ->
                result.onSuccessWithData { data ->
                    state.value = ChatHomeUiState(recentRooms = data)
                }.onFailure {
                    //TODO Handle error
                }
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        screenModelScope.launch {
            state.value = ChatHomeUiState()
            recentRoomRepository.onDispose()
        }
        println("Disposed")
    }
}
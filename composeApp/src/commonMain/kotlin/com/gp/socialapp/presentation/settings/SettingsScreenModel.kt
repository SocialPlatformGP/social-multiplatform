package com.gp.socialapp.presentation.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsScreenModel(
    private val authRepo: AuthenticationRepository,
): ScreenModel {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val state = _uiState.asStateFlow()
    init {
        getSignedInUser()
    }

    private fun getSignedInUser() {
        screenModelScope.launch {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Error -> {
                        /*TODO: Handle Error*/
                    }
                    is Result.SuccessWithData -> {
                        _uiState.update {
                            it.copy(currentUser = result.data)
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        _uiState.value = SettingsUiState()
    }

    fun onAction(action: SettingsAction) {
        when(action){
            SettingsAction.AccountSettingsAction.DeleteAccount -> TODO()
            is SettingsAction.AccountSettingsAction.UpdateBio -> TODO()
            is SettingsAction.AccountSettingsAction.UpdateName -> TODO()
            is SettingsAction.AccountSettingsAction.UpdatePhoneNumber -> TODO()
            is SettingsAction.AccountSettingsAction.UpdateAvatar -> TODO()
            is SettingsAction.DisplaySettingsAction.UpdateFontSize -> TODO()
            is SettingsAction.DisplaySettingsAction.UpdateLanguage -> TODO()
            is SettingsAction.DisplaySettingsAction.UpdateTheme -> TODO()
        }
    }
}
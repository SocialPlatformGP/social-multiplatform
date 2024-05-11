package com.gp.socialapp.presentation.grades.creator

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.grades.repository.GradesRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GradesCreatorScreenModel(
    private val gradesRepository: GradesRepository,
    private val authRepository: AuthenticationRepository
) : ScreenModel {
    private val _state = MutableStateFlow(GradesCreatorUiState())
    val state = _state.asStateFlow()

    fun init(communityId: String) {
        setCommunityId(communityId)
        getSignedInUser()
    }

    private fun getSignedInUser() {
        screenModelScope.launch {
            when (val result = authRepository.getSignedInUser()) {
                is Result.Error -> {
                }
                Result.Loading -> {
                }
                is Result.Success -> {
                    _state.update {
                        it.copy(user = result.data)
                    }
                    getCreatorGrades()
                }
            }
        }
    }

    private fun setCommunityId(communityId: String) {
        _state.update {
            it.copy(communityId = communityId)
        }
    }
    fun uploadGradesFile(
        name : String,
        type : String,
        content : ByteArray,
        subject : String,
    ) {
        screenModelScope.launch {
            val result = gradesRepository.uploadGradesFile(
                name = name,
                type = type,
                content = content,
                subject = subject,
                communityId = state.value.communityId,
                creatorId = state.value.user.id
            )
            when(result){
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    getCreatorGrades()
                }
            }
        }
    }

    private fun getCreatorGrades() {
        screenModelScope.launch {
            gradesRepository.getCreatorGrades(state.value.user.id).collect { result ->
                when(result) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            grades = result.data
                        )
                    }
                    is Result.Error -> {

                    }

                    Result.Loading -> {}
                }
            }
        }
    }
}
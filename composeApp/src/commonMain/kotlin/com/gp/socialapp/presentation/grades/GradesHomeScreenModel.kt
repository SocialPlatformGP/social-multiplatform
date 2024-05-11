package com.gp.socialapp.presentation.grades

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.grades.repository.GradesRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GradesHomeScreenModel(
    private val gradesRepository: GradesRepository,
    private val authRepository: AuthenticationRepository
): ScreenModel {
    private val _state = MutableStateFlow(GradesHomeState())
    val state = _state.asStateFlow()

    init {
        getSignedInUser()
    }

    private fun getSignedInUser() {
        screenModelScope.launch {
            val result = authRepository.getSignedInUser()
            when(result){
                is Result.Error -> {}
                Result.Idle -> TODO()
                Result.Loading -> {}
                Result.Success -> TODO()
                is Result.SuccessWithData -> {
                    _state.update {
                        it.copy(user = result.data)
                    }
                    loadGrades(result.data.id)
                }
            }
        }
    }

    private fun loadGrades(id: String) {
        screenModelScope.launch {
            gradesRepository.getGrades(id).collect { result ->
                when(result) {
                    is Result.SuccessWithData -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            grades = result.data
                        )
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message ?: "Unknown error"
                        )
                    }

                    Result.Idle -> TODO()
                    Result.Loading -> {}
                    Result.Success -> TODO()
                }
            }
        }
    }

    fun uploadGradesFile(
        name : String,
        type : String,
        content : ByteArray,
        subject : String,
        communityId : String
    ) {
        screenModelScope.launch {
            gradesRepository.uploadGradesFile(
                name = name,
                type = type,
                content = content,
                subject = subject,
                communityId = communityId
            )
        }
    }
}
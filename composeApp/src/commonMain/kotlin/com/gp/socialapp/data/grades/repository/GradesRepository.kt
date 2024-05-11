package com.gp.socialapp.data.grades.repository

import com.gp.socialapp.data.grades.model.Grades
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface GradesRepository {
    fun getGrades(userName: String):Flow<Result<List<Grades>>>
    suspend fun uploadGradesFile(name: String, type: String, content: ByteArray, subject: String, communityId: String)
}
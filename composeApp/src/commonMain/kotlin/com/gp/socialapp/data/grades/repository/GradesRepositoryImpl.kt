package com.gp.socialapp.data.grades.repository

import com.gp.socialapp.data.calendar.source.remote.CalendarRemoteDataSource
import com.gp.socialapp.data.grades.model.Grades
import com.gp.socialapp.data.grades.source.remote.GradesRemoteDataSource
import com.gp.socialapp.util.GradesError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class GradesRepositoryImpl (
    private val gradesRemoteDataSource: GradesRemoteDataSource
): GradesRepository {
    override fun getGrades(userName: String): Flow<Result<List<Grades>,GradesError>> {
        return gradesRemoteDataSource.getGrades(userName)
    }

    override suspend fun uploadGradesFile(
        name: String,
        type: String,
        content: ByteArray,
        subject: String,
        communityId: String,
        creatorId: String
    ): Result<Unit, GradesError> {
       return gradesRemoteDataSource.uploadGradesFile(name, type, content, subject, communityId, creatorId)
    }

    override fun getCreatorGrades(creatorId: String): Flow<Result<List<Grades>,GradesError>>{
        return gradesRemoteDataSource.getCreatorGrades(creatorId)
    }
}
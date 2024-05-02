package com.gp.socialapp.data.assignment.repository

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.source.remote.AssignmentRemoteDataSource
import com.gp.socialapp.data.assignment.source.remote.model.request.AssignmentRequest
import com.gp.socialapp.util.Result

class AssignmentRepositoryImpl(
    private val remoteDataSource: AssignmentRemoteDataSource
): AssignmentRepository {
    override suspend fun createAssignment(assignment: Assignment): Result<String> {
        val request = AssignmentRequest.CreateRequest(assignment)
        return remoteDataSource.createAssignment(request)
    }
}
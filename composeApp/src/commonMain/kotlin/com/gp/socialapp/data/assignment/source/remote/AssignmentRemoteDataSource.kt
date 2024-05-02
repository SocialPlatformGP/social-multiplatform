package com.gp.socialapp.data.assignment.source.remote

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.source.remote.model.request.AssignmentRequest
import com.gp.socialapp.util.Result

interface AssignmentRemoteDataSource {
    suspend fun createAssignment(request: AssignmentRequest.CreateRequest): Result<String>
}
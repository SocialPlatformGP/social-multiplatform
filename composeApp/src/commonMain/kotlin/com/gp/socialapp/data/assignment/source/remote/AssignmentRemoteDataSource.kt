package com.gp.socialapp.data.assignment.source.remote

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.util.Result

interface AssignmentRemoteDataSource {
    suspend fun createAssignment(assignment: Assignment): Result<String>
}
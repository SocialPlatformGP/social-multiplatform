package com.gp.socialapp.data.assignment.repository

import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.util.Result

interface AssignmentRepository {
    suspend fun createAssignment(
        assignment: Assignment
    ): Result<String>
}
package com.gp.socialapp.data.material.source.local

import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.util.MaterialError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MaterialLocalDataSource {
    suspend fun getFilePath(fileId: String): Flow<Result<MaterialFile,MaterialError>>
    suspend fun insertFile(file: MaterialFile): Result<Unit,MaterialError>
}


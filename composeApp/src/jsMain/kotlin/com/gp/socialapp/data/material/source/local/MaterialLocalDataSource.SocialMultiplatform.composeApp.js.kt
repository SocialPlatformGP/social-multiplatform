package com.gp.socialapp.data.material.source.local

import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

object DummyMaterialLocalDataSource : MaterialLocalDataSource {
    override suspend fun getFilePath(fileId: String): Flow<Result<MaterialFile>> {
        TODO()
    }

    override suspend fun insertFile(file: MaterialFile): Result<Unit> {
        TODO("Not yet implemented")
    }

}
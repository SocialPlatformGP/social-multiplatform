package com.gp.socialapp.data.material.source.local

import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.util.MaterialError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object DummyMaterialLocalDataSource : MaterialLocalDataSource {
    override suspend fun getFilePath(fileId: String): Flow<Result<MaterialFile, MaterialError.GetLocalFile>> = flow { }


    override suspend fun insertFile(file: MaterialFile): Result<Unit,MaterialError.InsertLocalFile> {
        return Result.Success(Unit)
    }

}
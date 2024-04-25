package com.gp.socialapp.data.material.source.local

import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.data.material.source.local.model.FileEntity
import com.gp.socialapp.data.material.source.local.model.FileEntity.Companion.toEntity
import com.gp.socialapp.util.Result
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MaterialLocalDataSourceImpl(
    val realm: Realm
) : MaterialLocalDataSource {
    override suspend fun getFilePath(fileId: String): Flow<Result<MaterialFile>> =
        flow {
            try {
                val file = realm.query(
                    clazz = FileEntity::class,
                    query = "id ==$0", fileId
                ).find().asFlow()
                file.collect {
                    when (it) {
                        is InitialResults<FileEntity> -> {
                            val data = it.list.firstOrNull()?.toMaterialFile()
                            if (data != null) {
                                emit(Result.SuccessWithData(data))
                            } else {
                                emit(Result.Error("File not found"))
                            }
                        }

                        is UpdatedResults -> {
                            val data = it.list.firstOrNull()?.toMaterialFile()
                            if (data != null) {
                                emit(Result.SuccessWithData(data))
                            } else {
                                emit(Result.Error("File not found"))
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
                e.printStackTrace()
            }
        }


    override suspend fun insertFile(file: MaterialFile): Result<Unit> {
        return try {
            realm.write {
                copyToRealm(
                    file.toEntity(),
                    updatePolicy = UpdatePolicy.ALL
                )
            }
            Result.Success
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }
}
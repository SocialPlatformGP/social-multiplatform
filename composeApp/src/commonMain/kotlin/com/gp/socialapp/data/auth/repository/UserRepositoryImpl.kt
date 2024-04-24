package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.DataSuccess
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userRemoteSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun updateUserInfo(user: User, pfpByteArray: ByteArray): Result<Nothing> {
        return if (pfpByteArray.isNotEmpty()) {
            val result = userRemoteSource.uploadUserPfp(pfpByteArray, user.id)
            if (result is Result.SuccessWithData) {
                userRemoteSource.updateUserInfo(user.copy(profilePictureURL = result.data))
            } else {
                Result.Error("An error occurred while uploading the profile picture")
            }
        } else {
            userRemoteSource.updateUserInfo(user)
        }
    }


    override fun fetchUsers() = userRemoteSource.fetchUsers()
    override fun getUsersByIds(Ids: List<String>) =
        userRemoteSource.getUsersByIds(GetUsersByIdsRequest(Ids))

    override suspend fun createRemoteUser(user: User): Results<DataSuccess.User, DataError.Network> =
        userRemoteSource.createRemoteUser(user)

    override fun getUserCommunities(userId: String): Flow<Results<List<Community>, DataError.Network>> =
        userRemoteSource.getUserCommunities(userId)


}
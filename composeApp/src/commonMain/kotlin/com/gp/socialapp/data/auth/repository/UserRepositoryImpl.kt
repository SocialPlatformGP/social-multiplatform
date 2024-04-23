package com.gp.socialapp.data.auth.repository

import com.eygraber.uri.Uri
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.util.Result

class UserRepositoryImpl(
    private val userRemoteSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun updateUserInfo(user: User, pfpByteArray: ByteArray): Result<Nothing>{
        return if (pfpByteArray.isNotEmpty()) {
            val result = userRemoteSource.uploadUserPfp(pfpByteArray, user.id)
            if(result is Result.SuccessWithData) {
                userRemoteSource.updateUserInfo(user.copy(profilePictureURL = result.data))
            } else {
                Result.Error("An error occurred while uploading the profile picture")
            }
        } else {
            userRemoteSource.updateUserInfo(user)
        }
    }

    override fun createUser(user: User, pfpURI: Uri) = userRemoteSource.createUser(user, pfpURI)
    override fun updateUser(user: User) = userRemoteSource.updateUser(user)
    override fun deleteUser(user: User) = userRemoteSource.deleteUser(user)
    override suspend fun fetchUser(email: String) = userRemoteSource.fetchUser(email)
    override fun fetchUsers() = userRemoteSource.fetchUsers()
    override fun getCurrentUserEmail() = userRemoteSource.getCurrentUserEmail()
    override fun getUsersByIds(Ids: List<String>)
        = userRemoteSource.getUsersByIds(GetUsersByIdsRequest(Ids))

    override suspend fun createRemoteUser(user: User): Result<Nothing> = userRemoteSource.createRemoteUser(user)

}
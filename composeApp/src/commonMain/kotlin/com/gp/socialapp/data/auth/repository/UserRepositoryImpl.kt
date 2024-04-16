package com.gp.socialapp.data.auth.repository

import com.eygraber.uri.Uri
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest

class UserRepositoryImpl(
    private val userRemoteSource: UserRemoteDataSource
) : UserRepository {
    override fun createUser(user: User, pfpURI: Uri) = userRemoteSource.createUser(user, pfpURI)
    override fun updateUser(user: User) = userRemoteSource.updateUser(user)
    override fun deleteUser(user: User) = userRemoteSource.deleteUser(user)
    override suspend fun fetchUser(email: String) = userRemoteSource.fetchUser(email)
    override fun fetchUsers() = userRemoteSource.fetchUsers()
    override fun getCurrentUserEmail() = userRemoteSource.getCurrentUserEmail()
    override fun getUsersByIds(Ids: List<String>)
        = userRemoteSource.getUsersByIds(GetUsersByIdsRequest(Ids))

}
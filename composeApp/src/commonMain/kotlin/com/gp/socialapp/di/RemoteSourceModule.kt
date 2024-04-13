package com.gp.socialapp.di

import com.eygraber.uri.Uri
import com.gp.material.model.FileType
import com.gp.material.model.MaterialItem
import com.gp.material.source.remote.MaterialRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSourceImpl
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSourceImpl
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.source.remote.MessageRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.MessageRemoteDataSourceImpl
import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSourceImpl
import com.gp.socialapp.data.chat.source.remote.RoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.RoomRemoteDataSourceImpl
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSource
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSourceImpl
import com.gp.socialapp.data.post.source.remote.ReplyRemoteDataSource
import com.gp.socialapp.data.post.source.remote.ReplyRemoteDataSourceImpl
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val remoteDataSourceModuleK = DI.Module("remoteDataSourceModule") {
    bind<PostRemoteDataSource>() with singleton { PostRemoteDataSourceImpl() }
    bind<ReplyRemoteDataSource>() with singleton { ReplyRemoteDataSourceImpl() }
    bind<AuthenticationRemoteDataSource>() with singleton { AuthenticationRemoteDataSourceImpl() }
    bind<MessageRemoteDataSource>() with singleton { MessageRemoteDataSourceImpl() }
    bind<RoomRemoteDataSource>() with singleton { RoomRemoteDataSourceImpl(instance()) }
    bind<RecentRoomRemoteDataSource>() with singleton { RecentRoomRemoteDataSourceImpl() }
    bind<UserRemoteDataSource>() with singleton { UserRemoteDataSourceImpl(instance()) }
    bind<HttpClient>() with singleton { HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                isLenient = true
                encodeDefaults = true
            })
        }
    } }
}
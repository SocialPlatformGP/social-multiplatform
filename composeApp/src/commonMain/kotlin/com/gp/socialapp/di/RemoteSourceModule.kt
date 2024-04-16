package com.gp.socialapp.di

import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSourceImpl
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSourceImpl
import com.gp.socialapp.data.chat.source.remote.MessageRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.MessageRemoteDataSourceImpl
import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSourceImpl
import com.gp.socialapp.data.chat.source.remote.RoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.RoomRemoteDataSourceImpl
import com.gp.socialapp.data.chat.source.remote.SocketService
import com.gp.socialapp.data.chat.source.remote.SocketServiceImpl
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSource
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSourceImpl
import com.gp.socialapp.data.post.source.remote.ReplyRemoteDataSource
import com.gp.socialapp.data.post.source.remote.ReplyRemoteDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import kotlin.time.Duration.Companion.seconds


val remoteDataSourceModuleK = DI.Module("remoteDataSourceModule") {
    bind<PostRemoteDataSource>() with singleton { PostRemoteDataSourceImpl() }
    bind<ReplyRemoteDataSource>() with singleton { ReplyRemoteDataSourceImpl() }
    bind<AuthenticationRemoteDataSource>() with singleton { AuthenticationRemoteDataSourceImpl() }
    bind<MessageRemoteDataSource>() with singleton {
        MessageRemoteDataSourceImpl(
            instance(),
            instance()
        )
    }
    bind<RoomRemoteDataSource>() with singleton { RoomRemoteDataSourceImpl(instance()) }
    bind<RecentRoomRemoteDataSource>() with singleton { RecentRoomRemoteDataSourceImpl(instance()) }
    bind<UserRemoteDataSource>() with singleton { UserRemoteDataSourceImpl(instance()) }
    bind<SocketService>() with singleton { SocketServiceImpl(instance()) }
    bind<HttpClient>() with singleton {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    isLenient = true
                    encodeDefaults = true
                })
            }
            install(WebSockets) {
                pingInterval = 30.seconds.inWholeMilliseconds
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }
    }
}


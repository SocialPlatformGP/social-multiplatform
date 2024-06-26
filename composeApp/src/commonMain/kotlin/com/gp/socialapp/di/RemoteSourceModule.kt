package com.gp.socialapp.di

import com.gp.socialapp.data.assignment.source.remote.AssignmentRemoteDataSource
import com.gp.socialapp.data.assignment.source.remote.AssignmentRemoteDataSourceImpl
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSourceImpl
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSourceImpl
import com.gp.socialapp.data.calendar.source.remote.CalendarRemoteDataSource
import com.gp.socialapp.data.calendar.source.remote.CalendarRemoteDataSourceImpl
import com.gp.socialapp.data.chat.source.remote.MessageRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.MessageRemoteDataSourceImpl
import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSourceImpl
import com.gp.socialapp.data.chat.source.remote.RoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.RoomRemoteDataSourceImpl
import com.gp.socialapp.data.community.source.remote.CommunityRemoteDataSource
import com.gp.socialapp.data.community.source.remote.CommunityRemoteDataSourceImpl
import com.gp.socialapp.data.grades.source.remote.GradesRemoteDataSource
import com.gp.socialapp.data.grades.source.remote.GradesRemoteDataSourceImpl
import com.gp.socialapp.data.material.source.remote.MaterialRemoteDataSource
import com.gp.socialapp.data.material.source.remote.MaterialRemoteDataSourceImpl
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
    bind<PostRemoteDataSource>() with singleton { PostRemoteDataSourceImpl(instance()) }
    bind<MaterialRemoteDataSource>() with singleton {
        MaterialRemoteDataSourceImpl(
            instance(),
            instance()
        )
    }
    bind<ReplyRemoteDataSource>() with singleton { ReplyRemoteDataSourceImpl(instance()) }
    bind<AuthenticationRemoteDataSource>() with singleton {
        AuthenticationRemoteDataSourceImpl(
            instance(),
            instance()
        )
    }
    bind<MessageRemoteDataSource>() with singleton {
        MessageRemoteDataSourceImpl(
            instance(),
        )
    }
    bind<RoomRemoteDataSource>() with singleton { RoomRemoteDataSourceImpl(instance()) }
    bind<RecentRoomRemoteDataSource>() with singleton { RecentRoomRemoteDataSourceImpl(instance()) }
    bind<UserRemoteDataSource>() with singleton { UserRemoteDataSourceImpl(instance(), instance()) }
    bind<CommunityRemoteDataSource>() with singleton { CommunityRemoteDataSourceImpl(instance()) }
    bind<AssignmentRemoteDataSource>() with singleton { AssignmentRemoteDataSourceImpl(instance()) }
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
    bind<CalendarRemoteDataSource>() with singleton {
        CalendarRemoteDataSourceImpl(
            instance(),
            instance()
        )
    }
    bind<GradesRemoteDataSource>() with singleton {
        GradesRemoteDataSourceImpl(
            instance(),
        )
    }
}


package com.gp.socialapp.di

import com.gp.socialapp.data.chat.source.local.MessageLocalDataSource
import com.gp.socialapp.data.chat.source.local.MessageLocalDataSourceImpl
import com.gp.socialapp.data.chat.source.local.RecentRoomLocalDataSource
import com.gp.socialapp.data.chat.source.local.RecentRoomLocalDataSourceImpl
import com.gp.socialapp.data.chat.source.local.RoomLocalDataSource
import com.gp.socialapp.data.chat.source.local.RoomLocalDataSourceImpl
import com.gp.socialapp.data.chat.source.local.model.MessageEntity
import com.gp.socialapp.data.db.provideDbDriver
import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.local.PostLocalDataSourceImpl
import com.gp.socialapp.db.AppDatabase
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

actual val platformModule = DI.Module("platformModule") {
    bind<AppDatabase>() with singleton {
        AppDatabase(provideDbDriver(AppDatabase.Schema))
    }
    bind<PostLocalDataSource>() with singleton { PostLocalDataSourceImpl(instance()) }
    bind<RoomLocalDataSource>() with singleton { RoomLocalDataSourceImpl() }
    bind<RecentRoomLocalDataSource>() with singleton { RecentRoomLocalDataSourceImpl() }
    bind<MessageLocalDataSource>() with singleton { MessageLocalDataSourceImpl(instance()) }
    bind<Configuration>() with singleton {
        RealmConfiguration.create(
            schema = setOf(MessageEntity::class)
        )
    }
    bind<Realm>() with singleton {
        Realm.open(instance())
    }
}
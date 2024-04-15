package com.gp.socialapp.di

import com.gp.socialapp.data.chat.source.local.MessageLocalDataSource
import com.gp.socialapp.data.chat.source.local.MessageLocalDataSourceImpl
import com.gp.socialapp.data.chat.source.local.RecentRoomLocalDataSource
import com.gp.socialapp.data.chat.source.local.RecentRoomLocalDataSourceImpl
import com.gp.socialapp.data.chat.source.local.RoomLocalDataSource
import com.gp.socialapp.data.chat.source.local.RoomLocalDataSourceImpl
import com.gp.socialapp.data.db.provideDbDriver
import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.local.PostLocalDataSourceImpl
import com.gp.socialapp.db.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
actual val platformModule = DI.Module("platformModule") {
    bind<AppDatabase>() with singleton {
        AppDatabase(provideDbDriver(AppDatabase.Schema))
    }
    bind<PostLocalDataSource>() with singleton { PostLocalDataSourceImpl(instance()) }
    bind<RoomLocalDataSource>() with singleton { RoomLocalDataSourceImpl() }
    bind<RecentRoomLocalDataSource>() with singleton { RecentRoomLocalDataSourceImpl() }
    bind<MessageLocalDataSource>() with singleton { MessageLocalDataSourceImpl() }
}
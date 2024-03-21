package com.gp.socialapp.di

import com.gp.socialapp.data.db.provideDbDriver
import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.local.PostLocalDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

actual val platformModule = DI.Module("platformModule") {
    bind<AppDatabase>() with singleton {
        AppDatabase(provideDbDriver(AppDatabase.Schema))
    }
    bind<PostLocalDataSource>() with singleton { PostLocalDataSourceImpl(instance()) }
}
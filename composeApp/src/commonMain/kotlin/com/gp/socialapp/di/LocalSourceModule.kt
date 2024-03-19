package com.gp.socialapp.di

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorageImpl
import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.local.PostLocalDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val localSourceModuleK = DI.Module("localSourceModule") {
    bind<AuthKeyValueStorage>() with singleton { AuthKeyValueStorageImpl() }
    bind<PostLocalDataSource>() with singleton { PostLocalDataSourceImpl(instance()) }
}

package com.gp.socialapp.di

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorageImpl
import org.koin.dsl.module

val localSourceModule = module {
    single<AuthKeyValueStorage> { AuthKeyValueStorageImpl() }
}
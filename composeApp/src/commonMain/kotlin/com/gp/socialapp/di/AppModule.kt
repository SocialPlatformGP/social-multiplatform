package com.gp.socialapp.di

import org.koin.dsl.module

val appModule = module {
    includes(remoteDataSourceModule, repositoryModule, screenModelModule)
}
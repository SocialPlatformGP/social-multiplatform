package com.gp.socialapp.di

import org.koin.dsl.module

val appModules = module {
    includes(remoteDataSourceModule, repositoryModule, screenModelModule)
}
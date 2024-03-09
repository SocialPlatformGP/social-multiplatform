package com.gp.socialapp.di

import com.gp.socialapp.presentation.auth.login.LoginScreenModel
import org.koin.dsl.module

val screenModelModule = module {
    factory { LoginScreenModel(get()) }
}
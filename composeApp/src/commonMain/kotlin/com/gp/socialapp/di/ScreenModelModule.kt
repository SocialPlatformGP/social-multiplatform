package com.gp.socialapp.di

import com.gp.socialapp.presentation.auth.login.LoginScreenModel
import com.gp.socialapp.presentation.auth.passwordreset.PasswordResetScreenModel
import com.gp.socialapp.presentation.auth.signup.SignUpScreenModel
import com.gp.socialapp.presentation.auth.userinfo.UserInformationScreenModel
import com.gp.socialapp.presentation.material.MaterialScreenModel
import org.koin.dsl.module

val screenModelModule = module {
    factory { LoginScreenModel(get()) }
    factory { PasswordResetScreenModel(get()) }
    factory { SignUpScreenModel(get()) }
    factory { MaterialScreenModel(get()) }
//    factory { UserInformationScreenModel(get())}
}
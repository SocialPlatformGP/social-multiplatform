package com.gp.socialapp.di

import com.gp.socialapp.presentation.auth.login.LoginScreenModel
import com.gp.socialapp.presentation.auth.passwordreset.PasswordResetScreenModel
import com.gp.socialapp.presentation.auth.signup.SignUpScreenModel
import com.gp.socialapp.presentation.auth.userinfo.UserInformationScreenModel
import com.gp.socialapp.presentation.post.create.CreatePostScreenModel
import com.gp.socialapp.presentation.post.edit.EditPostScreenModel
import com.gp.socialapp.presentation.post.feed.FeedScreenModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val screenModelModuleK = DI.Module("screenModelModule") {
    bind<CreatePostScreenModel>() with singleton { CreatePostScreenModel(instance(), instance()) }
    bind<LoginScreenModel>() with singleton { LoginScreenModel(instance()) }
    bind<PasswordResetScreenModel>() with singleton { PasswordResetScreenModel(instance()) }
    bind<SignUpScreenModel>() with singleton { SignUpScreenModel(instance()) }
    bind<UserInformationScreenModel>() with singleton { UserInformationScreenModel(instance()) }
    bind<FeedScreenModel>() with singleton { FeedScreenModel(instance()) }
    bind<EditPostScreenModel>() with singleton { EditPostScreenModel(instance()) }
}

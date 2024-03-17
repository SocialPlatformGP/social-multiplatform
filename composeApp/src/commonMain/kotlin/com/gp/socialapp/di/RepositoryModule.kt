package com.gp.socialapp.di

import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.AuthenticationRepositoryImpl
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.repository.UserRepositoryImpl
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.repository.PostRepositoryImpl
import com.gp.socialapp.data.post.repository.ReplyRepository
import com.gp.socialapp.data.post.repository.ReplyRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val repositoryModuleK = DI.Module("repositoryModule") {
    bind<UserRepository>() with singleton { UserRepositoryImpl(instance()) }
    bind<AuthenticationRepository>() with singleton {
        AuthenticationRepositoryImpl(
            instance(),
            instance()
        )
    }
    bind<PostRepository>() with singleton { PostRepositoryImpl(instance(), instance()) }
    bind<ReplyRepository>() with singleton {
        ReplyRepositoryImpl(
            instance(),
            instance(),
            instance()
        )
    }
}

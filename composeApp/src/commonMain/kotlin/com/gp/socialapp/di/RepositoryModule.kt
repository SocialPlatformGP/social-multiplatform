package com.gp.socialapp.di

import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.AuthenticationRepositoryImpl
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.repository.UserRepositoryImpl
import com.gp.socialapp.data.chat.repository.MessageRepository
import com.gp.socialapp.data.chat.repository.MessageRepositoryImpl
import com.gp.socialapp.data.chat.repository.RecentRoomRepository
import com.gp.socialapp.data.chat.repository.RecentRoomRepositoryImpl
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.data.chat.repository.RoomRepositoryImpl
import com.gp.socialapp.data.community.repository.CommunityRepository
import com.gp.socialapp.data.community.repository.CommunityRepositoryImpl
import com.gp.socialapp.data.material.repository.MaterialRepository
import com.gp.socialapp.data.material.repository.MaterialRepositoryImpl
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
    bind<MaterialRepository>() with singleton {
        MaterialRepositoryImpl(
            instance(), instance(), instance()
        )
    }
    bind<AuthenticationRepository>() with singleton {
        AuthenticationRepositoryImpl(
            instance(), instance()
        )
    }
    bind<PostRepository>() with singleton {
        PostRepositoryImpl(
            instance(), instance(), instance(), instance()
        )
    }
    bind<CommunityRepository>() with singleton {
        CommunityRepositoryImpl(
            instance(),
            instance(),
        )
    }
    bind<ReplyRepository>() with singleton { ReplyRepositoryImpl(instance(), instance()) }
    bind<MessageRepository>() with singleton { MessageRepositoryImpl(instance(), instance()) }
    bind<RecentRoomRepository>() with singleton { RecentRoomRepositoryImpl(instance(), instance()) }
    bind<RoomRepository>() with singleton { RoomRepositoryImpl(instance()) }
}




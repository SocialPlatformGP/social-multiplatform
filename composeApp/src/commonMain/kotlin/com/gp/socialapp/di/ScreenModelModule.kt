package com.gp.socialapp.di

import com.gp.socialapp.presentatDefaultn.post.postDetails.PostDetailsScreenModel
import com.gp.socialapp.presentation.auth.login.LoginScreenModel
import com.gp.socialapp.presentation.auth.passwordreset.PasswordResetScreenModel
import com.gp.socialapp.presentation.auth.signup.SignUpScreenModel
import com.gp.socialapp.presentation.auth.userinfo.UserInformationScreenModel
import com.gp.socialapp.presentation.chat.creategroup.CreateGroupScreenModel
import com.gp.socialapp.presentation.chat.home.ChatHomeScreenModel
import com.gp.socialapp.presentation.chat.private_chat.CreatePrivateChatScreenModel
import com.gp.socialapp.presentation.post.create.CreatePostScreenModel
import com.gp.socialapp.presentation.post.edit.EditPostScreenModel
import com.gp.socialapp.presentation.post.feed.FeedScreenModel
import com.gp.socialapp.presentation.post.search.SearchScreenModel
import com.gp.socialapp.presentation.post.searchResult.SearchResultScreenModel
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
    bind<FeedScreenModel>() with singleton { FeedScreenModel(instance(), instance()) }
    bind<EditPostScreenModel>() with singleton { EditPostScreenModel(instance()) }
    bind<PostDetailsScreenModel>() with singleton {
        PostDetailsScreenModel(
            instance(),
            instance(),
            instance()
        )
    }
    bind<SearchResultScreenModel>() with singleton { SearchResultScreenModel(instance()) }
    bind<SearchScreenModel>() with singleton { SearchScreenModel(instance()) }
    bind<CreateGroupScreenModel>() with singleton {
        CreateGroupScreenModel(
            instance(),
            instance(),
            instance()
        )
    }
    bind<ChatHomeScreenModel>() with singleton { ChatHomeScreenModel(instance(), instance()) }
    bind<CreatePrivateChatScreenModel>() with singleton {
        CreatePrivateChatScreenModel(
            instance(),
            instance(),
            instance()
        )
    }
}


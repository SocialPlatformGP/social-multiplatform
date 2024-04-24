package com.gp.socialapp.di

import com.gp.socialapp.presentation.auth.login.LoginScreenModel
import com.gp.socialapp.presentation.auth.passwordreset.PasswordResetScreenModel
import com.gp.socialapp.presentation.auth.signup.SignUpScreenModel
import com.gp.socialapp.presentation.chat.addmembers.AddMembersScreenModel
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreenModel
import com.gp.socialapp.presentation.chat.creategroup.CreateGroupScreenModel
import com.gp.socialapp.presentation.chat.createprivate.CreatePrivateChatScreenModel
import com.gp.socialapp.presentation.chat.groupdetails.GroupDetailsScreenModel
import com.gp.socialapp.presentation.chat.home.ChatHomeScreenModel
import com.gp.socialapp.presentation.community.createcommunity.CreateCommunityScreenModel
import com.gp.socialapp.presentation.main.userinfo.UserInformationScreenModel
import com.gp.socialapp.presentation.material.MaterialScreenModel
import com.gp.socialapp.presentation.post.create.CreatePostScreenModel
import com.gp.socialapp.presentation.post.edit.EditPostScreenModel
import com.gp.socialapp.presentation.post.feed.FeedScreenModel
import com.gp.socialapp.presentation.post.postDetails.PostDetailsScreenModel
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
    bind<UserInformationScreenModel>() with singleton {
        UserInformationScreenModel(
            instance(),
            instance()
        )
    }
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
    bind<ChatRoomScreenModel>() with singleton {
        ChatRoomScreenModel(
            instance(),
            instance(),
            instance()
        )
    }
    bind<GroupDetailsScreenModel>() with singleton {
        GroupDetailsScreenModel(
            instance(),
            instance(),
            instance()
        )
    }
    bind<AddMembersScreenModel>() with singleton {
        AddMembersScreenModel(
            instance(),
            instance(),
            instance()
        )
    }
    bind<MaterialScreenModel>() with singleton { MaterialScreenModel(instance()) }
    bind<CreateCommunityScreenModel>() with singleton {
        CreateCommunityScreenModel(
            instance(),
        )
    }
}


package com.gp.socialapp.di

import com.gp.socialapp.presentation.assignment.createassignment.CreateAssignmentScreenModel
import com.gp.socialapp.presentation.assignment.homeassignment.AssignmentHomeScreenModel
import com.gp.socialapp.presentation.assignment.submissionreview.SubmissionReviewScreenModel
import com.gp.socialapp.presentation.assignment.submitassignment.SubmitAssignmentScreenModel
import com.gp.socialapp.presentation.auth.login.LoginScreenModel
import com.gp.socialapp.presentation.auth.passwordreset.PasswordResetScreenModel
import com.gp.socialapp.presentation.auth.signup.SignUpScreenModel
import com.gp.socialapp.presentation.auth.userinfo.UserInformationScreenModel
import com.gp.socialapp.presentation.chat.addmembers.AddMembersScreenModel
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomScreenModel
import com.gp.socialapp.presentation.chat.creategroup.CreateGroupScreenModel
import com.gp.socialapp.presentation.chat.createprivate.CreatePrivateChatScreenModel
import com.gp.socialapp.presentation.chat.groupdetails.GroupDetailsScreenModel
import com.gp.socialapp.presentation.chat.home.ChatHomeScreenModel
import com.gp.socialapp.presentation.community.communityhome.CommunityHomeContainerScreenModel
import com.gp.socialapp.presentation.community.communitymembers.CommunityMembersScreenModel
import com.gp.socialapp.presentation.community.createcommunity.CreateCommunityScreenModel
import com.gp.socialapp.presentation.community.editcommunity.EditCommunityScreenModel
import com.gp.socialapp.presentation.home.container.HomeContainerScreenModel
import com.gp.socialapp.presentation.home.screen.HomeScreenModel
import com.gp.socialapp.presentation.material.MaterialScreenModel
import com.gp.socialapp.presentation.post.create.CreatePostScreenModel
import com.gp.socialapp.presentation.post.edit.EditPostScreenModel
import com.gp.socialapp.presentation.post.feed.FeedScreenModel
import com.gp.socialapp.presentation.post.postDetails.PostDetailsScreenModel
import com.gp.socialapp.presentation.post.search.SearchScreenModel
import com.gp.socialapp.presentation.post.searchResult.SearchResultScreenModel
import com.gp.socialapp.presentation.settings.SettingsScreenModel
import com.gp.socialapp.presentation.userprofile.UserProfileScreenModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val screenModelModuleK = DI.Module("screenModelModule") {
    bind<CreatePostScreenModel>() with singleton { CreatePostScreenModel(instance(), instance()) }
    bind<LoginScreenModel>() with singleton { LoginScreenModel(instance(), instance()) }
    bind<PasswordResetScreenModel>() with singleton { PasswordResetScreenModel(instance()) }
    bind<SignUpScreenModel>() with singleton { SignUpScreenModel(instance()) }
    bind<UserInformationScreenModel>() with singleton {
        UserInformationScreenModel(
            instance(),
            instance()
        )
    }
    bind<SubmissionReviewScreenModel>() with singleton {
        SubmissionReviewScreenModel(instance())
    }
    bind<FeedScreenModel>() with singleton { FeedScreenModel(instance(), instance(), instance()) }
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
    bind<CreateAssignmentScreenModel>() with singleton {
        CreateAssignmentScreenModel(
            instance(),
            instance()
        )
    }
    bind<MaterialScreenModel>() with singleton { MaterialScreenModel(instance()) }
    bind<HomeScreenModel>() with singleton { HomeScreenModel(instance(), instance(), instance()) }

    bind<CreateCommunityScreenModel>() with singleton {
        CreateCommunityScreenModel(
            instance(),
            instance()
        )
    }
    bind<CommunityMembersScreenModel>() with singleton {
        CommunityMembersScreenModel(
            instance(),
            instance(),
            instance()
        )
    }
    bind<EditCommunityScreenModel>() with singleton {
        EditCommunityScreenModel(
            instance(),
        )
    }
    bind<CommunityHomeContainerScreenModel>() with singleton {
        CommunityHomeContainerScreenModel(
            instance(),
            instance()
        )
    }
    bind<HomeContainerScreenModel>() with singleton {
        HomeContainerScreenModel(
            instance(),
        )
    }
    bind<UserProfileScreenModel>() with singleton {
        UserProfileScreenModel(
            instance(),
            instance(),
            instance()
        )
    }
    bind<SettingsScreenModel>() with singleton { SettingsScreenModel(instance(), instance()) }
    bind<SubmitAssignmentScreenModel>() with singleton { SubmitAssignmentScreenModel(instance(),instance()) }
    bind<AssignmentHomeScreenModel>() with singleton { AssignmentHomeScreenModel(instance(),instance()) }
}


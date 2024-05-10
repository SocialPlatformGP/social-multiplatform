package com.gp.socialapp.util

import kotlinx.serialization.Serializable

@Serializable
sealed interface Error


@Serializable
sealed interface AssignmentError : Error {

    @Serializable
    enum class CreateAssignment(val userMessage: String) : AssignmentError {
        ASSIGNMENT_CREATED_SUCCESSFULLY("Assignment created successfully"),
        TITLE_CANNOT_BE_EMPTY("Title cannot be empty"),
        MAX_POINTS_CANNOT_BE_EMPTY("Max points cannot be empty"),
        CREATOR_ID_CANNOT_BE_EMPTY("Creator id cannot be empty"),
        CREATOR_NAME_CANNOT_BE_EMPTY("Creator name cannot be empty"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetAttachments(val userMessage: String) : AssignmentError {
        ATTACHMENTS_FETCHED_SUCCESSFULLY("Attachments fetched successfully"),
        USER_ID_CANNOT_BE_EMPTY("User id cannot be empty"),
        ASSIGNMENT_ID_CANNOT_BE_EMPTY("Assignment id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class SubmitAssignment(val userMessage: String) : AssignmentError {
        ASSIGNMENT_SUBMITTED_SUCCESSFULLY("Assignment submitted successfully"),
        ASSIGNMENT_ID_CANNOT_BE_EMPTY("Assignment id cannot be empty"),
        USER_ID_CANNOT_BE_EMPTY("User id cannot be empty"),
        ATTACHMENTS_CANNOT_BE_EMPTY("Attachments cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetAssignments(val userMessage: String) : AssignmentError {
        ASSIGNMENTS_FETCHED_SUCCESSFULLY("Assignments fetched successfully"),
        USER_ID_CANNOT_BE_EMPTY("User id cannot be empty"),
        SERVER_ERROR("Server error"),

    }

    @Serializable
    enum class GetAssignment(val userMessage: String) : AssignmentError {
        ASSIGNMENT_FETCHED_SUCCESSFULLY("Assignment fetched successfully"),
        ASSIGNMENT_ID_CANNOT_BE_EMPTY("Assignment id cannot be empty"),
        SERVER_ERROR("Server error"),

    }

    @Serializable
    enum class GetSubmissions(val userMessage: String) : AssignmentError {
        ASSIGNMENT_SUBMISSIONS_FETCHED_SUCCESSFULLY("Assignment submissions fetched successfully"),
        ASSIGNMENT_ID_CANNOT_BE_EMPTY("Assignment id cannot be empty"),
        SERVER_ERROR("Server error"),

    }

    @Serializable
    enum class SubmitReview(val userMessage: String) : AssignmentError {
        REVIEW_SUBMITTED_SUCCESSFULLY("Review submitted successfully"),
        ASSIGNMENT_ID_CANNOT_BE_EMPTY("Assignment id cannot be empty"),
        GRADE_CANNOT_BE_EMPTY("Grade cannot be empty"),
        SERVER_ERROR("Server error"),

    }

    @Serializable
    enum class TurnInAssignments(val userMessage: String) : AssignmentError {
        ASSIGNMENT_TURNED_IN_SUCCESSFULLY("Assignment turned in successfully"),
        USER_ASSIGNMENT_SUBMISSION_ID_CANNOT_BE_EMPTY("User assignment submission id cannot be empty"),
        SERVER_ERROR("Server error"),

    }

    @Serializable
    enum class UnSubmitAssignment(val userMessage: String) : AssignmentError {
        ASSIGNMENT_UN_SUBMITTED_SUCCESSFULLY("Assignment un-submitted successfully"),
        USER_ASSIGNMENT_SUBMISSION_ID_CANNOT_BE_EMPTY("User assignment submission id cannot be empty"),
        SERVER_ERROR("Server error"),

    }
}

@Serializable
sealed interface AuthError : Error {

    @Serializable
    enum class SignInWithOAuth(val userMessage: String) : AuthError {
        SIGNED_IN_SUCCESSFULLY("Signed in successfully"),
        NETWORK_ERROR("Network error"),
        CANT_GET_USER_DATA("Can't get user data"),
        NOT_AUTHENTICATED("Not authenticated"),
    }
    @Serializable
    enum class PasswordReset(val userMessage: String) : AuthError {
        PASSWORD_RESET_SUCCESSFULLY("Password reset successfully"),
        EMAIL_CANNOT_BE_EMPTY("Email cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class SignInWithEmail(val userMessage: String) : AuthError {
        SIGNED_IN_SUCCESSFULLY("Signed in successfully"),
        EMAIL_CANNOT_BE_EMPTY("Email cannot be empty"),
        CANT_GET_USER_DATA("Can't get user data"),

        PASSWORD_CANNOT_BE_EMPTY("Password cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class SignUpWithEmail(val userMessage: String) : AuthError {
        SIGNED_UP_SUCCESSFULLY("Signed up successfully"),
        EMAIL_CANNOT_BE_EMPTY("Email cannot be empty"),
        CANT_GET_USER_DATA("Can't get user data"),

        PASSWORD_CANNOT_BE_EMPTY("Password cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetSignedInUser(val userMessage: String) : AuthError {
        USER_FETCHED_SUCCESSFULLY("User fetched successfully"),
        USER_FETCH_FAILED("User fetch failed"),
        USER_NOT_FOUND("User not found"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class Logout(val userMessage: String) : AuthError {
        LOGGED_OUT_SUCCESSFULLY("Logged out successfully"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class DeleteUser(val userMessage: String) : AuthError {
        USER_DELETED_SUCCESSFULLY("User deleted successfully"),
        USER_ID_CANNOT_BE_EMPTY("User id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetUserSettings(val userMessage: String) : AuthError {
        USER_SETTINGS_FETCHED_SUCCESSFULLY("User settings fetched successfully"),
        USER_CANNOT_BE_FOUND("User cannot be found"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class ChangePassword(val userMessage: String) : AuthError {
        PASSWORD_CHANGED_SUCCESSFULLY("Password changed successfully"),
        PASSWORD_CANNOT_BE_EMPTY("Password cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class ChangeEmail(val userMessage: String) : AuthError {
        EMAIL_CHANGED_SUCCESSFULLY("Email changed successfully"),
        EMAIL_CANNOT_BE_EMPTY("Email cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class UpdateUserSetting(val userMessage: String) : AuthError {
        USER_SETTING_UPDATED_SUCCESSFULLY("User setting updated successfully"),
        TAGS_CANNOT_BE_EMPTY("Tags cannot be empty"),
        VALUES_CANNOT_BE_EMPTY("Values cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class SendPasswordResetEmail(val userMessage: String) : AuthError {
        PASSWORD_RESET_EMAIL_SENT_SUCCESSFULLY("Password reset email sent successfully"),
        EMAIL_CANNOT_BE_EMPTY("Email cannot be empty"),
        SERVER_ERROR("Server error"),
    }
}

@Serializable
sealed interface CalendarError : Error {
    @Serializable
    enum class GetEvents(val userMessage: String) : CalendarError {
        EVENTS_FETCHED_SUCCESSFULLY("Events fetched successfully"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class CreateEvent(val userMessage: String) : CalendarError {
        EVENT_CREATED_SUCCESSFULLY("Event created successfully"),
        TITLE_CANNOT_BE_EMPTY("Title cannot be empty"),
        DESCRIPTION_CANNOT_BE_EMPTY("Description cannot be empty"),
        START_TIME_CANNOT_BE_EMPTY("Start time cannot be empty"),
        END_TIME_CANNOT_BE_EMPTY("End time cannot be empty"),
        SERVER_ERROR("Server error"),
    }

}

@Serializable
sealed interface ChatError : Error {
    @Serializable
    enum class Temp(val userMessage: String) : ChatError {
        SERVER_ERROR("Server error"),
    }
}

@Serializable
sealed interface CommunityError : Error {
    @Serializable
    enum class CreateCommunity(val userMessage: String) : CommunityError {
        COMMUNITY_CREATED_SUCCESSFULLY("Community created successfully"),
        TITLE_CANNOT_BE_EMPTY("Title cannot be empty"),
        DESCRIPTION_CANNOT_BE_EMPTY("Description cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class DeleteCommunity(val userMessage: String) : CommunityError {
        COMMUNITY_DELETED_SUCCESSFULLY("Community deleted successfully"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class UpdateCommunity(val userMessage: String) : CommunityError {
        COMMUNITY_UPDATED_SUCCESSFULLY("Community updated successfully"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty"),
        TITLE_CANNOT_BE_EMPTY("Title cannot be empty"),
        DESCRIPTION_CANNOT_BE_EMPTY("Description cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetCommunities(val userMessage: String) : CommunityError {
        COMMUNITIES_FETCHED_SUCCESSFULLY("Communities fetched successfully"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetCommunity(val userMessage: String) : CommunityError {
        COMMUNITY_FETCHED_SUCCESSFULLY("Community fetched successfully"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class JoinCommunity(val userMessage: String) : CommunityError {
        JOINED_COMMUNITY_SUCCESSFULLY("Joined community successfully"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class LeaveCommunity(val userMessage: String) : CommunityError {
        LEFT_COMMUNITY_SUCCESSFULLY("Left community successfully"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetCommunityMembers(val userMessage: String) : CommunityError {
        COMMUNITY_MEMBERS_FETCHED_SUCCESSFULLY("Community members fetched successfully"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetCommunityAssignments(val userMessage: String) : CommunityError {
        COMMUNITY_ASSIGNMENTS_FETCHED_SUCCESSFULLY("Community assignments fetched successfully"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetCommunityEvents(val userMessage: String) : CommunityError {
        COMMUNITY_EVENTS_FETCHED_SUCCESSFULLY("Community events fetched successfully"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetCommunityPosts(val userMessage: String) : CommunityError {
        COMMUNITY_POSTS_FETCHED_SUCCESSFULLY("Community posts fetched successfully"),
        COMMUNITY_ID_CANNOT_BE_EMPTY("Community id cannot be empty "),
    }

    @Serializable
    enum class AcceptCommunityRequest(val userMessage: String) : CommunityError {
        COMMUNITY_REQUEST_ACCEPTED_SUCCESSFULLY("Community request accepted successfully"),
        COMMUNITY_REQUEST_ID_CANNOT_BE_EMPTY("Community request id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class DeclineCommunityRequest(val userMessage: String) : CommunityError {
        COMMUNITY_REQUEST_DECLINED_SUCCESSFULLY("Community request declined successfully"),
        COMMUNITY_REQUEST_ID_CANNOT_BE_EMPTY("Community request id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
}

@Serializable
sealed interface MaterialError : Error {
    @Serializable
    enum class GetMaterial(val userMessage: String) : MaterialError {
        MATERIAL_FETCHED_SUCCESSFULLY("Material fetched successfully"),
        MATERIAL_ID_CANNOT_BE_EMPTY("Material id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class CreateFolder(val userMessage: String) : MaterialError {
        FOLDER_CREATED_SUCCESSFULLY("Folder created successfully"),
        TITLE_CANNOT_BE_EMPTY("Title cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class CreateFile(val userMessage: String) : MaterialError {
        FILE_CREATED_SUCCESSFULLY("File created successfully"),
        TITLE_CANNOT_BE_EMPTY("Title cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class DeleteFile(val userMessage: String) : MaterialError {
        FILE_DELETED_SUCCESSFULLY("File deleted successfully"),
        FILE_ID_CANNOT_BE_EMPTY("File id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class DeleteFolder(val userMessage: String) : MaterialError {
        FOLDER_DELETED_SUCCESSFULLY("Folder deleted successfully"),
        FOLDER_ID_CANNOT_BE_EMPTY("Folder id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class RenameFolder(val userMessage: String) : MaterialError {
        FOLDER_RENAMED_SUCCESSFULLY("Folder renamed successfully"),
        FOLDER_ID_CANNOT_BE_EMPTY("Folder id cannot be empty"),
        TITLE_CANNOT_BE_EMPTY("Title cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class GetLocalFile(val userMessage: String) : MaterialError {
        FILE_FETCHED_SUCCESSFULLY("File fetched successfully"),
        FILE_ID_CANNOT_BE_EMPTY("File id cannot be empty"),
        DATABASE_ERROR("Database error"),
    }
    @Serializable
    enum class InsertLocalFile(val userMessage: String) : MaterialError {
        FILE_INSERTED_SUCCESSFULLY("File inserted successfully"),
        FILE_ID_CANNOT_BE_EMPTY("File id cannot be empty"),
        DATABSE_ERROR("Database error"),
    }
    @Serializable
    enum class DownloadFile(val userMessage: String) : MaterialError {
        FILE_DOWNLOADED_SUCCESSFULLY("File downloaded successfully"),
        FILE_ID_CANNOT_BE_EMPTY("File id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
}

@Serializable
sealed interface PostError: Error {
    @Serializable
    enum class CreatePost(val userMessage: String) : PostError {
        POST_CREATED_SUCCESSFULLY("Post created successfully"),
        TITLE_CANNOT_BE_EMPTY("Title cannot be empty"),
        CONTENT_CANNOT_BE_EMPTY("Content cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class DeletePost(val userMessage: String) : PostError {
        POST_DELETED_SUCCESSFULLY("Post deleted successfully"),
        POST_ID_CANNOT_BE_EMPTY("Post id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class UpdatePost(val userMessage: String) : PostError {
        POST_UPDATED_SUCCESSFULLY("Post updated successfully"),
        POST_ID_CANNOT_BE_EMPTY("Post id cannot be empty"),
        TITLE_CANNOT_BE_EMPTY("Title cannot be empty"),
        CONTENT_CANNOT_BE_EMPTY("Content cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetPosts(val userMessage: String) : PostError {
        POSTS_FETCHED_SUCCESSFULLY("Posts fetched successfully"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class GetPost(val userMessage: String) : PostError {
        POST_FETCHED_SUCCESSFULLY("Post fetched successfully"),
        POST_ID_CANNOT_BE_EMPTY("Post id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class UpvotePost(val userMessage: String) : PostError {
        POST_UPVOTED_SUCCESSFULLY("Post upvoted successfully"),
        POST_ID_CANNOT_BE_EMPTY("Post id cannot be empty"),
        SERVER_ERROR("Server error"),
    }

    @Serializable
    enum class DownvotePost(val userMessage: String) : PostError {
        POST_DOWNVOTED_SUCCESSFULLY("Post downvoted successfully"),
        POST_ID_CANNOT_BE_EMPTY("Post id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class ReportPost(val userMessage: String) : PostError {
        POST_REPORTED_SUCCESSFULLY("Post reported successfully"),
        POST_ID_CANNOT_BE_EMPTY("Post id cannot be empty"),
        REPORTER_ID_CANNOT_BE_EMPTY("Reporter id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class SearchByTitle(val userMessage: String) : PostError {
        POSTS_FETCHED_SUCCESSFULLY("Posts fetched successfully"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class SearchByTag(val userMessage: String) : PostError {
        POSTS_FETCHED_SUCCESSFULLY("Posts fetched successfully"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class GetUserPosts(val userMessage: String) : PostError {
        POSTS_FETCHED_SUCCESSFULLY("Posts fetched successfully"),
        SERVER_ERROR("Server error"),
    }

}

@Serializable
sealed interface ReplyError : Error {
    @Serializable
    enum class InsertReply(val userMessage: String) : ReplyError {
        REPLY_CREATED_SUCCESSFULLY("Reply created successfully"),
        CONTENT_CANNOT_BE_EMPTY("Content cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class DeleteReply(val userMessage: String) : ReplyError {
        REPLY_DELETED_SUCCESSFULLY("Reply deleted successfully"),
        REPLY_ID_CANNOT_BE_EMPTY("Reply id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class UpdateReply(val userMessage: String) : ReplyError {
        REPLY_UPDATED_SUCCESSFULLY("Reply updated successfully"),
        REPLY_ID_CANNOT_BE_EMPTY("Reply id cannot be empty"),
        CONTENT_CANNOT_BE_EMPTY("Content cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class UpvoteReply(val userMessage: String) : ReplyError {
        REPLY_UPVOTED_SUCCESSFULLY("Reply upvoted successfully"),
        REPLY_ID_CANNOT_BE_EMPTY("Reply id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class DownvoteReply(val userMessage: String) : ReplyError {
        REPLY_DOWNVOTED_SUCCESSFULLY("Reply downvoted successfully"),
        REPLY_ID_CANNOT_BE_EMPTY("Reply id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class ReportReply(val userMessage: String) : ReplyError {
        REPLY_REPORTED_SUCCESSFULLY("Reply reported successfully"),
        REPLY_ID_CANNOT_BE_EMPTY("Reply id cannot be empty"),
        REPORTER_ID_CANNOT_BE_EMPTY("Reporter id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class GetReplies(val userMessage: String) : ReplyError {
        REPLIES_FETCHED_SUCCESSFULLY("Replies fetched successfully"),
        POST_ID_CANNOT_BE_EMPTY("Post id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class GetReply(val userMessage: String) : ReplyError {
        REPLY_FETCHED_SUCCESSFULLY("Reply fetched successfully"),
        REPLY_ID_CANNOT_BE_EMPTY("Reply id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
}
@Serializable
sealed interface UserError : Error {
    @Serializable
    enum class UpdateUserInfo(val userMessage: String) : UserError {
        USER_UPDATED_SUCCESSFULLY("User updated successfully"),
        USER_ID_CANNOT_BE_EMPTY("User id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class UpdatePhoneNumber (val userMessage: String) : UserError {
        PHONE_NUMBER_UPDATED_SUCCESSFULLY("Phone number updated successfully"),
        PHONE_NUMBER_CANNOT_BE_EMPTY("Phone number cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class UpdateName (val userMessage: String) : UserError {
        NAME_UPDATED_SUCCESSFULLY("Name updated successfully"),
        NAME_CANNOT_BE_EMPTY("Name cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class GetUserSettings (val userMessage: String) : UserError {
        USER_SETTINGS_FETCHED_SUCCESSFULLY("User settings fetched successfully"),
        USER_ID_CANNOT_BE_EMPTY("User id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class UpdateUserAvatar (val userMessage: String) : UserError {
        AVATAR_UPDATED_SUCCESSFULLY("Avatar updated successfully"),
        AVATAR_CANNOT_BE_EMPTY("Avatar cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class ChangePassword (val userMessage: String) : UserError {
        PASSWORD_CHANGED_SUCCESSFULLY("Password changed successfully"),
        PASSWORD_CANNOT_BE_EMPTY("Password cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class ChangeEmail (val userMessage: String) : UserError {
        EMAIL_CHANGED_SUCCESSFULLY("Email changed successfully"),
        EMAIL_CANNOT_BE_EMPTY("Email cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class UpdateUserSetting (val userMessage: String) : UserError {
        USER_SETTING_UPDATED_SUCCESSFULLY("User setting updated successfully"),
        TAGS_CANNOT_BE_EMPTY("Tags cannot be empty"),
        VALUES_CANNOT_BE_EMPTY("Values cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class FetchUsers (val userMessage: String) : UserError {
        USERS_FETCHED_SUCCESSFULLY("Users fetched successfully"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class GetUsersByIds (val userMessage: String) : UserError {
        USERS_FETCHED_SUCCESSFULLY("Users fetched successfully"),
        USER_IDS_CANNOT_BE_EMPTY("User ids cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class CreateRemoteUser (val userMessage: String) : UserError {
        USER_CREATED_SUCCESSFULLY("User created successfully"),
        USER_CANNOT_BE_EMPTY("User cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class GetUserCommunities (val userMessage: String) : UserError {
        USER_COMMUNITIES_FETCHED_SUCCESSFULLY("User communities fetched successfully"),
        USER_ID_CANNOT_BE_EMPTY("User id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class GetTheme (val userMessage: String) : UserError {
        THEME_FETCHED_SUCCESSFULLY("Theme fetched successfully"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class SetTheme (val userMessage: String) : UserError {
        THEME_SET_SUCCESSFULLY("Theme set successfully"),
        THEME_CANNOT_BE_EMPTY("Theme cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class CommunityLogout (val userMessage: String) : UserError {
        COMMUNITY_LOGOUT_SUCCESSFULLY("Community logout successfully"),
        ID_CANNOT_BE_EMPTY("Id cannot be empty"),
        SELECTED_COMMUNITY_ID_CANNOT_BE_EMPTY("Selected community id cannot be empty"),
        SERVER_ERROR("Server error"),
    }
    @Serializable
    enum class JoinCommunity (val userMessage: String) : UserError {
        JOINED_COMMUNITY_SUCCESSFULLY("Joined community successfully"),
        ID_CANNOT_BE_EMPTY("Id cannot be empty"),
        CODE_CANNOT_BE_EMPTY("Code cannot be empty"),
        SERVER_ERROR("Server error"),
    }

}



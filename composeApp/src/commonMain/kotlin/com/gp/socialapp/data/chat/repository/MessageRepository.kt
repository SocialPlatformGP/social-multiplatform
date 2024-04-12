package com.gp.socialapp.data.chat.repository

interface MessageRepository {
//    fun fetchGroupChatMessages(groupId: String): Flow<List<Message>>
//    fun createGroupChat(name: String, avatarLink: String, members: List<String>, currentUserEmail: String): Flow<Result<String>>
//    fun insertChat(chat: ChatRoom): Flow<Result<String>>
//    fun insertRecentChat(recentChat: RecentChat,chatId: String)
//    fun sendMessage(message: Message): Flow<Result<String>>
//    fun getMessages(chatId: String): Flow<Result<List<Message>>>
//    fun getRecentChats(chatId: List<String>): Flow<Result<List<RecentChat>>>
//    fun insertChatToUser(chatId:String,userEmail: String,receiverEmail:String): Flow<Result<String>>
//    fun getUserChats(userEmail: String): Flow<Result<ChatUser>>
//    fun insertPrivateChat(sender:String,receiver:String,chatId: String): Flow<Result<String>>
//    fun haveChatWithUser(userEmail: String, otherUserEmail: String): Flow<Result<String>>
//    fun updateRecentChat(recentChat: RecentChat, chatId: String): Flow<Result<String>>
//    fun deleteMessage(messageId: String,chatId: String)
//    fun updateMessage(messageId: String,chatId: String, updatedText: String)
//    fun leaveGroup(chatId: String)
//    fun getGroupDetails(groupId: String): Flow<Result<ChatGroup>>
//    fun removeMemberFromGroup(groupId: String, memberEmail: String): Flow<Result<String>>
//    fun updateGroupAvatar(uri: Uri, oldURL: String, groupID: String): Flow<Result<String>>
//    fun addGroupMembers(groupId: String, usersEmails: List<String>): Flow<Result<Nothing>>
//    fun changeGroupName(groupID: String, newName: String): Flow<Result<Nothing>>
}
package com.gp.socialapp.presentation.chat.chatroom

import cafe.adriel.voyager.core.model.ScreenModel
import com.gp.socialapp.data.chat.repository.MessageRepository
import com.gp.socialapp.data.chat.repository.RoomRepository

class ChatRoomScreenModel(
    private val messageRepo: MessageRepository,
    private val roomRepo: RoomRepository,
) : ScreenModel
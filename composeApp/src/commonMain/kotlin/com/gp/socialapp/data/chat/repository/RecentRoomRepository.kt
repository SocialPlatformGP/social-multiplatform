package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow


interface RecentRoomRepository {
    fun fetchRecentRooms(userId: String): Flow<Result<List<RecentRoom>,ChatError>>

}
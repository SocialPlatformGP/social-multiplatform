package com.gp.socialapp.data.chat.source.remote


import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.data.chat.model.UserRooms
import com.gp.socialapp.data.chat.source.remote.model.RemoteRecentRoom
import com.gp.socialapp.util.Result
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresListDataFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecentRoomRemoteDataSourceImpl(
    private val supabase: SupabaseClient
) : RecentRoomRemoteDataSource {
    private val userRoomsChannel = supabase.channel("user_rooms")
    private val recentRoomsChannel = supabase.channel("messages")
    override fun fetchRecentRooms(
        userId: String,
        scope: CoroutineScope
    ): Flow<Result<List<RecentRoom>>> = flow {
        emit(Result.Loading)
        try {
            userRoomsChannel.postgresListDataFlow(
                schema = "public",
                table = "user_rooms",
                primaryKey = UserRooms::userId,
                filter = FilterOperation("userId", FilterOperator.EQ, userId)
            ).onEach { userRooms ->
                recentRoomsChannel.postgresListDataFlow(
                    schema = "public",
                    table = "recent_rooms",
                    primaryKey = RemoteRecentRoom::roomId,
                    filter = FilterOperation("roomId", FilterOperator.IN, userRooms)
                ).onEach { remoteRecentRooms ->
                    emit(Result.SuccessWithData(remoteRecentRooms.map { remoteRecentRoom -> remoteRecentRoom.toRecentRoom() }
                        .sortedByDescending { recentRoom -> recentRoom.lastMessageTime }))
                }.launchIn(scope)
            }.launchIn(scope)
        } catch (e: Exception) {
            emit(Result.Error("Error fetching recent rooms: ${e.message}"))
        }
    }


    override suspend fun onDispose() {
        userRoomsChannel.unsubscribe()
        recentRoomsChannel.unsubscribe()
    }

}
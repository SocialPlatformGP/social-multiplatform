package com.gp.socialapp.data.chat.source.remote


import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.data.chat.model.UserRooms
import com.gp.socialapp.data.chat.source.remote.model.RemoteRecentRoom
import com.gp.socialapp.util.Result
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import io.github.jan.supabase.realtime.selectSingleValueAsFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RecentRoomRemoteDataSourceImpl(
    private val supabase: SupabaseClient
) : RecentRoomRemoteDataSource {
    private val USERROOMS = "user_rooms"
    private val RECENTROOMS = "recent_rooms"

    @OptIn(SupabaseExperimental::class)
    override fun fetchRecentRooms(
        userId: String, scope: CoroutineScope
    ): Flow<Result<List<RecentRoom>>> = callbackFlow {
        trySend(Result.Loading)
        try {
            supabase.from(USERROOMS).selectSingleValueAsFlow(UserRooms::userId) {
                eq("userId", userId)
            }.collect {
                println("received data in remote source from user_rooms :$it")
                val roomsString =
                    it.rooms.joinToString(separator = ",", prefix = "(", postfix = ")")
                supabase.from(RECENTROOMS).selectAsFlow(
                    RemoteRecentRoom::roomId,
                    filter = FilterOperation("roomId", FilterOperator.IN, roomsString)
                ).collect {
                    println("received data in remote source from recent_rooms :$it")
                    trySend(Result.SuccessWithData(it.map { remoteRecentRoom -> remoteRecentRoom.toRecentRoom() }
                        .sortedByDescending { recentRoom -> recentRoom.lastMessageTime }))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(Result.Error("Error fetching recent rooms: ${e.message}"))
        }
        awaitClose()
    }

}
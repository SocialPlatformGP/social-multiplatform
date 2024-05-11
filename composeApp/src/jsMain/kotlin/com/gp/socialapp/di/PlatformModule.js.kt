package com.gp.socialapp.di


import com.gp.socialapp.data.chat.source.local.DummyMessageLocalSource
import com.gp.socialapp.data.chat.source.local.MessageLocalDataSource
import com.gp.socialapp.data.chat.source.local.RecentRoomLocalDataSource
import com.gp.socialapp.data.chat.source.local.RoomLocalDataSource
import com.gp.socialapp.data.material.source.local.DummyMaterialLocalDataSource
import com.gp.socialapp.data.material.source.local.MaterialLocalDataSource
import com.gp.socialapp.data.material.utils.FileManager
import com.gp.socialapp.data.post.source.local.DummyPostLocalDataSource
import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.presentation.app.com.gp.socialapp.data.chat.source.local.DummyFileManager
import com.gp.socialapp.presentation.app.com.gp.socialapp.data.chat.source.local.DummyRecentRoomLocalSource
import com.gp.socialapp.presentation.app.com.gp.socialapp.data.chat.source.local.DummyRoomLocalSource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.FlowType
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

actual val platformModule = DI.Module("platformModule") {
    bind<PostLocalDataSource>() with singleton { DummyPostLocalDataSource }
    bind<MessageLocalDataSource>() with singleton { DummyMessageLocalSource }
    bind<RoomLocalDataSource>() with singleton { DummyRoomLocalSource }
    bind<RecentRoomLocalDataSource>() with singleton { DummyRecentRoomLocalSource }
    bind<MaterialLocalDataSource>() with singleton { DummyMaterialLocalDataSource }
    bind<FileManager>() with singleton { DummyFileManager }
    bind<SupabaseClient>() with singleton {
        createSupabaseClient(
            supabaseUrl = "https://vszvbwfzewqeoxxpetgj.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZzenZid2Z6ZXdxZW94eHBldGdqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTM2MjM0MjUsImV4cCI6MjAyOTE5OTQyNX0.dO4SiJ9MCN0gZaY15kjqRdYL0NRFTZWID_xiYWhAnk8"
        ) {
            install(Auth){
                flowType = FlowType.PKCE
                host = "com.gp.socialapp"
                scheme = "edulink"
            }
            install(Storage)
            install(Postgrest)
            install(Realtime)
        }
    }
}
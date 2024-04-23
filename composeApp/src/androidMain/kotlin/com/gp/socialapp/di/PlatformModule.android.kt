package com.gp.socialapp.di

import com.gp.socialapp.data.chat.source.local.MessageLocalDataSource
import com.gp.socialapp.data.chat.source.local.MessageLocalDataSourceImpl
import com.gp.socialapp.data.chat.source.local.RecentRoomLocalDataSource
import com.gp.socialapp.data.chat.source.local.RecentRoomLocalDataSourceImpl
import com.gp.socialapp.data.chat.source.local.RoomLocalDataSource
import com.gp.socialapp.data.chat.source.local.RoomLocalDataSourceImpl
import com.gp.socialapp.data.chat.source.local.model.MessageEntity
import com.gp.socialapp.data.db.provideDbDriver
import com.gp.socialapp.data.material.source.local.MaterialLocalDataSource
import com.gp.socialapp.data.material.source.local.MaterialLocalDataSourceImpl
import com.gp.socialapp.data.material.source.local.model.FileEntity
import com.gp.socialapp.data.material.source.remote.FileManagerImpl
import com.gp.socialapp.data.material.utils.FileManager
import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.local.PostLocalDataSourceImpl
import com.gp.socialapp.db.AppDatabase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.ExternalAuthAction
import io.github.jan.supabase.gotrue.FlowType
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

actual val platformModule = DI.Module("platformModule") {
    bind<AppDatabase>() with singleton {
        AppDatabase(provideDbDriver(AppDatabase.Schema))
    }
    bind<PostLocalDataSource>() with singleton { PostLocalDataSourceImpl(instance()) }
    bind<RoomLocalDataSource>() with singleton { RoomLocalDataSourceImpl() }
    bind<RecentRoomLocalDataSource>() with singleton { RecentRoomLocalDataSourceImpl() }
    bind<MessageLocalDataSource>() with singleton { MessageLocalDataSourceImpl(instance()) }
    bind<Configuration>() with singleton {
        RealmConfiguration.create(
            schema = setOf(MessageEntity::class, FileEntity::class),
        )
    }
    bind<Realm>() with singleton {
        Realm.open(instance())
    }
    bind<MaterialLocalDataSource>() with singleton { MaterialLocalDataSourceImpl(instance()) }

    bind<FileManager>() with singleton { FileManagerImpl() }

    bind<SupabaseClient>() with singleton {
        createSupabaseClient(
            supabaseUrl = "https://vszvbwfzewqeoxxpetgj.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZzenZid2Z6ZXdxZW94eHBldGdqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTM2MjM0MjUsImV4cCI6MjAyOTE5OTQyNX0.dO4SiJ9MCN0gZaY15kjqRdYL0NRFTZWID_xiYWhAnk8"
        ) {
            install(Auth) {
                flowType = FlowType.PKCE
                host = "login"
                scheme = "com.gp.edulink"
                defaultExternalAuthAction = ExternalAuthAction.CustomTabs()
            }
        }
    }
}
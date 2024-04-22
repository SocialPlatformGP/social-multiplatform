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
}
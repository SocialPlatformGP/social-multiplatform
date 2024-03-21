package com.gp.socialapp.di


import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.presentation.app.com.gp.socialapp.data.post.source.local.DummyLocalSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

actual val platformModule = DI.Module("platformModule") {
    bind<PostLocalDataSource>() with singleton { DummyLocalSource }
}
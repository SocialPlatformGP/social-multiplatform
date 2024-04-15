package com.gp.socialapp.di

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorageImpl
import com.russhwolf.settings.Settings
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val localSourceModuleK = DI.Module("localSourceModule") {
    bind<Settings>() with singleton { Settings() }
    bind<AuthKeyValueStorage>() with singleton { AuthKeyValueStorageImpl(instance()) }


}


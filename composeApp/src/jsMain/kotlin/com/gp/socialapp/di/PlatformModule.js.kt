package com.gp.socialapp.di


import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
actual val platformModule =  DI.Module("platformModule") {
//    bind<SqlDriver>() with singleton {
//        DatabaseDriver().createDriver()
//    }
//    bind<AppDatabase>() with singleton { AppDatabase(instance()) }
//    bind<PostQueries>() with singleton { instance<AppDatabase>().postQueries }
}
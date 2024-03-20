package com.gp.socialapp.di


import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
actual val platformModule = DI.Module("platformModule") {
//    bind<SqlDriver>() with singleton {
//        DatabaseDriver().createDriver()
//    }
//    bind<AppDatabase>() with singleton { AppDatabase(instance()) }
//    bind<PostQueries>() with singleton { instance<AppDatabase>().postQueries }
}
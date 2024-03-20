package com.gp.socialapp.di

import org.kodein.di.DI

actual val platformModule = DI.Module("platformModule") {
//    bind<AppDatabase>() with singleton { AppDatabase(provideDbDriver(AppDatabase.Schema)) }
//    bind<PostQueries>() with singleton { instance<AppDatabase>().postQueries }
}
package com.gp.socialapp.di

import org.kodein.di.DI


val appModuleK = DI {
    import(remoteDataSourceModuleK)
    import(repositoryModuleK)
    import(screenModelModuleK)
    import(localSourceModuleK)
}

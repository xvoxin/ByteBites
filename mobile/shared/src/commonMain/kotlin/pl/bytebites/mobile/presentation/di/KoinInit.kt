package pl.bytebites.mobile.presentation.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import pl.bytebites.mobile.data.repository.di.repositoryModule
import pl.bytebites.mobile.domain.di.domainModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            domainModule,
            repositoryModule,
            presentationModule
        )
    }

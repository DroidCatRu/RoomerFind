package ru.droidcat.feature.root.internal.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.droidcat.feature.auth.internal.ui.root.di.authModule
import ru.droidcat.feature.main.internal.di.mainModule
import ru.droidcat.feature.root.internal.DefaultRootComponent
import ru.droidcat.feature.root.internal.DefaultRootExecutor
import ru.droidcat.feature.root.internal.DefaultRootReducer
import ru.droidcat.feature.root.internal.DefaultRootStore

val rootModule = module {
    includes(
        platformModule,
        authModule,
        mainModule,
    )

    singleOf<StoreFactory>(::DefaultStoreFactory)

    scope<DefaultRootComponent> {
        scopedOf(::DefaultRootExecutor)
        scopedOf(::DefaultRootReducer)
        scopedOf(::DefaultRootStore)
    }
}

expect val platformModule: Module

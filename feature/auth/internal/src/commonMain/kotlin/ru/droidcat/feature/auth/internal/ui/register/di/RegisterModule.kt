package ru.droidcat.feature.auth.internal.ui.register.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.auth.internal.ui.register.DefaultRegisterComponent
import ru.droidcat.feature.auth.internal.ui.register.DefaultRegisterExecutor
import ru.droidcat.feature.auth.internal.ui.register.DefaultRegisterReducer
import ru.droidcat.feature.auth.internal.ui.register.DefaultRegisterStore

val registerModule = module {
    scope<DefaultRegisterComponent> {
        scopedOf(::DefaultRegisterExecutor)
        scopedOf(::DefaultRegisterReducer)
        scopedOf(::DefaultRegisterStore)
    }
}

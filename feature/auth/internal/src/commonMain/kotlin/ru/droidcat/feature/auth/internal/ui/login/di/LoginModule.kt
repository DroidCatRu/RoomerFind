package ru.droidcat.feature.auth.internal.ui.login.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.auth.internal.ui.login.DefaultLoginComponent
import ru.droidcat.feature.auth.internal.ui.login.DefaultLoginExecutor
import ru.droidcat.feature.auth.internal.ui.login.DefaultLoginReducer
import ru.droidcat.feature.auth.internal.ui.login.DefaultLoginStore

val loginModule = module {
    scope<DefaultLoginComponent> {
        scopedOf(::DefaultLoginExecutor)
        scopedOf(::DefaultLoginReducer)
        scopedOf(::DefaultLoginStore)
    }
}

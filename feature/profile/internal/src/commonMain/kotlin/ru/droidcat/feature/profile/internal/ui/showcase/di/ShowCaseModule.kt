package ru.droidcat.feature.profile.internal.ui.showcase.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.profile.internal.ui.showcase.DefaultShowCaseComponent
import ru.droidcat.feature.profile.internal.ui.showcase.DefaultShowCaseExecutor
import ru.droidcat.feature.profile.internal.ui.showcase.DefaultShowCaseReducer
import ru.droidcat.feature.profile.internal.ui.showcase.DefaultShowCaseStore

val showCaseModule = module {
    scope<DefaultShowCaseComponent> {
        scopedOf(::DefaultShowCaseExecutor)
        scopedOf(::DefaultShowCaseReducer)
        scopedOf(::DefaultShowCaseStore)
    }
}

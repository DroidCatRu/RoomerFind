package ru.droidcat.feature.finders.internal.ui.profile.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.finders.internal.ui.profile.DefaultFinderProfileComponent
import ru.droidcat.feature.finders.internal.ui.profile.DefaultProfileExecutor
import ru.droidcat.feature.finders.internal.ui.profile.DefaultProfileReducer
import ru.droidcat.feature.finders.internal.ui.profile.DefaultProfileStore

val finderProfileModule = module {
    scope<DefaultFinderProfileComponent> {
        scopedOf(::DefaultProfileExecutor)
        scopedOf(::DefaultProfileReducer)
        scopedOf(::DefaultProfileStore)
    }
}

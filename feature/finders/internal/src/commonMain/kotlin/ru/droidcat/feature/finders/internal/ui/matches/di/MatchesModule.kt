package ru.droidcat.feature.finders.internal.ui.matches.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.finders.internal.ui.matches.DefaultMatchesComponent
import ru.droidcat.feature.finders.internal.ui.matches.DefaultMatchesExecutor
import ru.droidcat.feature.finders.internal.ui.matches.DefaultMatchesReducer
import ru.droidcat.feature.finders.internal.ui.matches.DefaultMatchesStore

val matchesModule = module {
    scope<DefaultMatchesComponent> {
        scopedOf(::DefaultMatchesExecutor)
        scopedOf(::DefaultMatchesReducer)
        scopedOf(::DefaultMatchesStore)
    }
}

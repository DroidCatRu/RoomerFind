package ru.droidcat.feature.finders.internal.ui.search.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.finders.internal.ui.search.DefaultSearchComponent
import ru.droidcat.feature.finders.internal.ui.search.DefaultSearchExecutor
import ru.droidcat.feature.finders.internal.ui.search.DefaultSearchReducer
import ru.droidcat.feature.finders.internal.ui.search.DefaultSearchStore

val searchModule = module {
    scope<DefaultSearchComponent> {
        scopedOf(::DefaultSearchExecutor)
        scopedOf(::DefaultSearchReducer)
        scopedOf(::DefaultSearchStore)
    }
}

package ru.droidcat.feature.profile.internal.ui.preferenceedit.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.profile.internal.ui.preferenceedit.DefaultPreferenceEditComponent
import ru.droidcat.feature.profile.internal.ui.preferenceedit.DefaultPreferenceEditExecutor
import ru.droidcat.feature.profile.internal.ui.preferenceedit.DefaultPreferenceEditReducer
import ru.droidcat.feature.profile.internal.ui.preferenceedit.DefaultPreferenceEditStore

val preferenceEditModule = module {
    scope<DefaultPreferenceEditComponent> {
        scopedOf(::DefaultPreferenceEditExecutor)
        scopedOf(::DefaultPreferenceEditReducer)
        scopedOf(::DefaultPreferenceEditStore)
    }
}

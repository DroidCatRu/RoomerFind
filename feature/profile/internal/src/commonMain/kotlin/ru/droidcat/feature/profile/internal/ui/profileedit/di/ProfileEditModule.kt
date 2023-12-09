package ru.droidcat.feature.profile.internal.ui.profileedit.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.profile.internal.ui.profileedit.DefaultProfileEditComponent
import ru.droidcat.feature.profile.internal.ui.profileedit.DefaultProfileEditExecutor
import ru.droidcat.feature.profile.internal.ui.profileedit.DefaultProfileEditReducer
import ru.droidcat.feature.profile.internal.ui.profileedit.DefaultProfileEditStore

val profileEditModule = module {
    scope<DefaultProfileEditComponent> {
        scopedOf(::DefaultProfileEditExecutor)
        scopedOf(::DefaultProfileEditReducer)
        scopedOf(::DefaultProfileEditStore)
    }
}

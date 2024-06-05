package ru.droidcat.feature.main.internal.di

import org.koin.dsl.module
import ru.droidcat.feature.finders.internal.ui.root.di.findersModule
import ru.droidcat.feature.profile.internal.ui.root.di.profileModule

val mainModule = module {
    includes(
        profileModule,
        findersModule,
    )
}

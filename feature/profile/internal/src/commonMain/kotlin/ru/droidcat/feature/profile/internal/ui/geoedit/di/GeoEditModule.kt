package ru.droidcat.feature.profile.internal.ui.geoedit.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.map.internal.di.mapModule
import ru.droidcat.feature.profile.internal.ui.geoedit.DefaultGeoEditComponent
import ru.droidcat.feature.profile.internal.ui.geoedit.DefaultGeoEditExecutor
import ru.droidcat.feature.profile.internal.ui.geoedit.DefaultGeoEditReducer
import ru.droidcat.feature.profile.internal.ui.geoedit.DefaultGeoEditStore

val geoEditModule = module {
    includes(
        mapModule,
    )
    scope<DefaultGeoEditComponent> {
        scopedOf(::DefaultGeoEditExecutor)
        scopedOf(::DefaultGeoEditReducer)
        scopedOf(::DefaultGeoEditStore)
    }
}

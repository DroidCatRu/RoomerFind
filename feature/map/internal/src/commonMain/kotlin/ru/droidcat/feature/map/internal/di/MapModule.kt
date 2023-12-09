package ru.droidcat.feature.map.internal.di

import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module
import ru.droidcat.feature.map.internal.DefaultMapComponent
import ru.droidcat.feature.map.internal.DefaultMapExecutor
import ru.droidcat.feature.map.internal.DefaultMapReducer
import ru.droidcat.feature.map.internal.DefaultMapStore

val mapModule = module {
    scope<DefaultMapComponent> {
        scopedOf(::DefaultMapExecutor)
        scopedOf(::DefaultMapReducer)
        scopedOf(::DefaultMapStore)
    }
}

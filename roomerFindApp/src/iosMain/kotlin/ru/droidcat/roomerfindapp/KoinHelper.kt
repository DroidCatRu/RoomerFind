package ru.droidcat.roomerfindapp

import org.koin.core.context.startKoin
import ru.droidcat.feature.root.internal.di.rootModule

fun initKoin() {
    startKoin {
        modules(rootModule)
    }
}

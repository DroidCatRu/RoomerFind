package ru.droidcat.feature.root.internal.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.droidcat.roomerfind.model.network.UrlProvider
import ru.droidcat.roomerfind.model.network.UrlProviderAndroid

actual val platformModule = module {
    singleOf(::UrlProviderAndroid).bind<UrlProvider>()
}

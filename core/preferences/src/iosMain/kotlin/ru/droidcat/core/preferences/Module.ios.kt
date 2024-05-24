package ru.droidcat.core.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults
import ru.droidcat.core.coroutines.ioDispatcher

@OptIn(ExperimentalSettingsApi::class)
internal actual val DIPlatform = module {
    single {
        NSUserDefaultsSettings(NSUserDefaults.new()!!)
            .toFlowSettings(dispatcher = ioDispatcher)
    }
}

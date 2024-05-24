package ru.droidcat.core.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.binds
import org.koin.dsl.module

@OptIn(ExperimentalSettingsApi::class)
val preferencesModule = module {
    singleOf(::PreferencesImpl) binds arrayOf(
        Preferences::class,
        AdvancedPreferences::class,
    )
    includes(DIPlatform)
}

internal expect val DIPlatform: Module
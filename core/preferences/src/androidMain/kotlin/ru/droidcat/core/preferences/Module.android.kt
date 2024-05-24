package ru.droidcat.core.preferences

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.datastore.DataStoreSettings
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

@OptIn(ExperimentalSettingsApi::class, ExperimentalSettingsImplementation::class)
internal actual val DIPlatform = module {
    singleOf(::DataStoreSettings).bind<FlowSettings>()
    single {
        PreferenceDataStoreFactory.create {
            val defaultPreferencesName = get<Context>().packageName + "_preferences"
            File(get<Context>().filesDir, "datastore/$defaultPreferencesName.preferences_pb")
        }
    }
}
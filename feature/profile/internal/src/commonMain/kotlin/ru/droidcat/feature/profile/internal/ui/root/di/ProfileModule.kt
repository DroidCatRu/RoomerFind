package ru.droidcat.feature.profile.internal.ui.root.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.droidcat.feature.profile.api.data.ProfileApi
import ru.droidcat.feature.profile.api.usecase.GetContactsUseCase
import ru.droidcat.feature.profile.api.usecase.GetGeoUseCase
import ru.droidcat.feature.profile.api.usecase.GetPreferenceUseCase
import ru.droidcat.feature.profile.api.usecase.GetProfileUseCase
import ru.droidcat.feature.profile.api.usecase.SaveContactsUseCase
import ru.droidcat.feature.profile.api.usecase.SaveGeoUseCase
import ru.droidcat.feature.profile.api.usecase.SavePreferenceUseCase
import ru.droidcat.feature.profile.api.usecase.SaveProfileUseCase
import ru.droidcat.feature.profile.internal.data.DefaultProfileApi
import ru.droidcat.feature.profile.internal.ui.geoedit.di.geoEditModule
import ru.droidcat.feature.profile.internal.ui.preferenceedit.di.preferenceEditModule
import ru.droidcat.feature.profile.internal.ui.profileedit.di.profileEditModule
import ru.droidcat.feature.profile.internal.ui.showcase.di.showCaseModule

val profileModule = module {
    includes(
        showCaseModule,
        profileEditModule,
        preferenceEditModule,
        geoEditModule,
    )

    singleOf<ProfileApi>(::DefaultProfileApi)

    factoryOf(::GetContactsUseCase)
    factoryOf(::GetGeoUseCase)
    factoryOf(::GetPreferenceUseCase)
    factoryOf(::GetProfileUseCase)
    factoryOf(::SaveContactsUseCase)
    factoryOf(::SaveGeoUseCase)
    factoryOf(::SavePreferenceUseCase)
    factoryOf(::SaveProfileUseCase)
}

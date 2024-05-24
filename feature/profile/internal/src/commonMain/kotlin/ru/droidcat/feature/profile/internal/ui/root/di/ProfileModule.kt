package ru.droidcat.feature.profile.internal.ui.root.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.droidcat.feature.profile.api.data.LocalProfileApi
import ru.droidcat.feature.profile.api.usecase.GetProfileUseCase
import ru.droidcat.feature.profile.api.usecase.SaveProfileUseCase
import ru.droidcat.feature.profile.internal.data.LocalProfileImpl
import ru.droidcat.feature.profile.internal.data.RemoteProfileRepository
import ru.droidcat.feature.profile.internal.ui.geoedit.di.geoEditModule
import ru.droidcat.feature.profile.internal.ui.preferenceedit.di.preferenceEditModule
import ru.droidcat.feature.profile.internal.ui.profileedit.di.profileEditModule
import ru.droidcat.feature.profile.internal.ui.showcase.di.showCaseModule
import ru.droidcat.feature.profile.internal.usecase.GetProfileUseCaseImpl
import ru.droidcat.feature.profile.internal.usecase.SaveProfileUseCaseImpl

val profileModule = module {
    includes(
        showCaseModule,
        profileEditModule,
        preferenceEditModule,
        geoEditModule,
    )

    singleOf(::LocalProfileImpl).bind<LocalProfileApi>()
    singleOf(::RemoteProfileRepository)
    singleOf(::GetProfileUseCaseImpl).bind<GetProfileUseCase>()
    singleOf(::SaveProfileUseCaseImpl).bind<SaveProfileUseCase>()
}

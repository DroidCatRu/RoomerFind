package ru.droidcat.feature.finders.internal.ui.root.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.droidcat.feature.finders.api.data.FindersLocalApi
import ru.droidcat.feature.finders.api.usecase.GetFinderUseCase
import ru.droidcat.feature.finders.api.usecase.GetMatchesUseCase
import ru.droidcat.feature.finders.api.usecase.SelectedProfileUseCase
import ru.droidcat.feature.finders.api.usecase.SendReactionUseCase
import ru.droidcat.feature.finders.internal.data.FindersLocalImpl
import ru.droidcat.feature.finders.internal.data.RemoteFindersRepository
import ru.droidcat.feature.finders.internal.ui.matches.di.matchesModule
import ru.droidcat.feature.finders.internal.ui.profile.di.finderProfileModule
import ru.droidcat.feature.finders.internal.ui.search.di.searchModule
import ru.droidcat.feature.finders.internal.usecase.GetFinderUseCaseImpl
import ru.droidcat.feature.finders.internal.usecase.GetMatchesUseCaseImpl
import ru.droidcat.feature.finders.internal.usecase.SelectedProfileUseCaseImpl
import ru.droidcat.feature.finders.internal.usecase.SendReactionUseCaseImpl

val findersModule = module {
    includes(
        matchesModule,
        searchModule,
        finderProfileModule,
    )

    singleOf(::FindersLocalImpl).bind<FindersLocalApi>()
    singleOf(::RemoteFindersRepository)
    singleOf(::GetMatchesUseCaseImpl).bind<GetMatchesUseCase>()
    singleOf(::GetFinderUseCaseImpl).bind<GetFinderUseCase>()
    singleOf(::SendReactionUseCaseImpl).bind<SendReactionUseCase>()
    singleOf(::SelectedProfileUseCaseImpl).bind<SelectedProfileUseCase>()
}

package ru.droidcat.feature.finders.internal.ui.root.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.droidcat.feature.finders.api.data.FindersApi
import ru.droidcat.feature.finders.api.usecase.GetMatchesUseCase
import ru.droidcat.feature.finders.internal.data.DefaultFindersApi
import ru.droidcat.feature.finders.internal.ui.matches.di.matchesModule
import ru.droidcat.feature.finders.internal.ui.search.di.searchModule

val findersModule = module {
    includes(
        matchesModule,
        searchModule,
    )

    singleOf<FindersApi>(::DefaultFindersApi)

    factoryOf(::GetMatchesUseCase)
}

package ru.droidcat.feature.finders.internal.ui.matches

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesIntent
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState
import ru.droidcat.feature.finders.internal.ui.matches.model.Action.Init
import ru.droidcat.feature.finders.internal.ui.matches.model.Label

private const val STORE_NAME = "MatchesStore"

internal class DefaultMatchesStore(
    storeFactory: StoreFactory,
    executor: DefaultMatchesExecutor,
    reducer: DefaultMatchesReducer,
) : Store<MatchesIntent, MatchesState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = MatchesState.Loading,
    bootstrapper = SimpleBootstrapper(Init),
    executorFactory = { executor },
    reducer = reducer,
)

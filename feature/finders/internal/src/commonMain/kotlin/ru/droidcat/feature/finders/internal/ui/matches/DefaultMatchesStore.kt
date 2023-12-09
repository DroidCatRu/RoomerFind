package ru.droidcat.feature.finders.internal.ui.matches

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState
import ru.droidcat.feature.finders.internal.ui.matches.model.Action.GetMatches
import ru.droidcat.feature.finders.internal.ui.matches.model.Intent
import ru.droidcat.feature.finders.internal.ui.matches.model.Label

private const val STORE_NAME = "MatchesStore"

internal class DefaultMatchesStore(
    storeFactory: StoreFactory,
    executor: DefaultMatchesExecutor,
    reducer: DefaultMatchesReducer,
) : Store<Intent, MatchesState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = MatchesState.Loaded(),
//    bootstrapper = SimpleBootstrapper(
//        GetMatches,
//    ),
    executorFactory = { executor },
    reducer = reducer,
)

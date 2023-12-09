package ru.droidcat.feature.finders.internal.ui.search

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.finders.api.ui.search.model.SearchState
import ru.droidcat.feature.finders.internal.ui.search.model.Intent
import ru.droidcat.feature.finders.internal.ui.search.model.Label

private const val STORE_NAME = "SearchStore"

internal class DefaultSearchStore(
    storeFactory: StoreFactory,
    executor: DefaultSearchExecutor,
    reducer: DefaultSearchReducer
) : Store<Intent, SearchState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = SearchState.Loading,
    executorFactory = { executor },
    reducer = reducer,
)

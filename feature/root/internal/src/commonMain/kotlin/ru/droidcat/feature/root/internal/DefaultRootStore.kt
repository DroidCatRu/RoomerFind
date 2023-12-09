package ru.droidcat.feature.root.internal

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.root.api.model.RootState
import ru.droidcat.feature.root.internal.model.Intent
import ru.droidcat.feature.root.internal.model.Label

private const val STORE_NAME = "RootStore"

internal class DefaultRootStore(
    storeFactory: StoreFactory,
    executor: DefaultRootExecutor,
    reducer: DefaultRootReducer,
) : Store<Intent, RootState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = RootState,
    executorFactory = { executor },
    reducer = reducer,
)

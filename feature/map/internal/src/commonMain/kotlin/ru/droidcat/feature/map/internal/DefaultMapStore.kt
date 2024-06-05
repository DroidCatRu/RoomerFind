package ru.droidcat.feature.map.internal

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.map.api.model.MapIntent
import ru.droidcat.feature.map.api.model.MapState
import ru.droidcat.feature.map.internal.model.Label

private const val STORE_NAME = "MapStore"

internal class DefaultMapStore(
    storeFactory: StoreFactory,
    executor: DefaultMapExecutor,
    reducer: DefaultMapReducer,
) : Store<MapIntent, MapState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = MapState(),
    executorFactory = { executor },
    reducer = reducer,
)

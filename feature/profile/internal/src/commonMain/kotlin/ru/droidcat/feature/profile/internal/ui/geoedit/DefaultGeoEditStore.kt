package ru.droidcat.feature.profile.internal.ui.geoedit

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState.Loading
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Action.GetUserGeo
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label

private const val STORE_NAME = "GeoEditStore"

internal class DefaultGeoEditStore(
    storeFactory: StoreFactory,
    executor: DefaultGeoEditExecutor,
    reducer: DefaultGeoEditReducer,
) : Store<GeoEditIntent, GeoEditState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = Loading,
    bootstrapper = SimpleBootstrapper(
        GetUserGeo,
    ),
    executorFactory = { executor },
    reducer = reducer,
)

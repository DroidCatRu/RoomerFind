package ru.droidcat.feature.finders.internal.ui.profile

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.finders.api.ui.profile.model.FinderProfileIntent
import ru.droidcat.feature.finders.api.ui.profile.model.ProfileState
import ru.droidcat.feature.finders.internal.ui.profile.model.Action.Init
import ru.droidcat.feature.finders.internal.ui.profile.model.Label

private const val STORE_NAME = "FinderProfileStore"

internal class DefaultProfileStore(
    storeFactory: StoreFactory,
    executor: DefaultProfileExecutor,
    reducer: DefaultProfileReducer,
) : Store<FinderProfileIntent, ProfileState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = ProfileState.Loading,
    bootstrapper = SimpleBootstrapper(Init),
    executorFactory = { executor },
    reducer = reducer,
)

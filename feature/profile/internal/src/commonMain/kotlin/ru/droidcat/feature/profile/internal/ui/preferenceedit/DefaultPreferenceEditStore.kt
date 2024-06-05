package ru.droidcat.feature.profile.internal.ui.preferenceedit

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Action.GetProfile
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Label

private const val STORE_NAME = "PreferenceEditStore"

internal class DefaultPreferenceEditStore(
    storeFactory: StoreFactory,
    executor: DefaultPreferenceEditExecutor,
    reducer: DefaultPreferenceEditReducer,
) : Store<PreferenceEditIntent, PreferenceState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = PreferenceState.Loading,
    bootstrapper = SimpleBootstrapper(GetProfile),
    executorFactory = { executor },
    reducer = reducer,
)

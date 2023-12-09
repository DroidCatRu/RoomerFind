package ru.droidcat.feature.profile.internal.ui.showcase

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState
import ru.droidcat.feature.profile.internal.ui.showcase.model.Action.GetUserProfile
import ru.droidcat.feature.profile.internal.ui.showcase.model.Intent
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label

private const val STORE_NAME = "ShowCaseStore"

internal class DefaultShowCaseStore(
    storeFactory: StoreFactory,
    executor: DefaultShowCaseExecutor,
    reducer: DefaultShowCaseReducer,
) : Store<Intent, ProfileShowCaseState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = ProfileShowCaseState.Loading,
    bootstrapper = SimpleBootstrapper(
        GetUserProfile,
    ),
    executorFactory = { executor },
    reducer = reducer,
)

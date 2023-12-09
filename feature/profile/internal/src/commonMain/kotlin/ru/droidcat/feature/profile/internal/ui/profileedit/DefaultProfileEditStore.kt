package ru.droidcat.feature.profile.internal.ui.profileedit

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Action.GetUserProfile
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Label

private const val STORE_NAME = "ProfileEditStore"

internal class DefaultProfileEditStore(
    storeFactory: StoreFactory,
    executor: DefaultProfileEditExecutor,
    reducer: DefaultProfileEditReducer,
) : Store<Intent, ProfileEditState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = ProfileEditState.Loading,
    bootstrapper = SimpleBootstrapper(
        GetUserProfile,
    ),
    executorFactory = { executor },
    reducer = reducer,
)

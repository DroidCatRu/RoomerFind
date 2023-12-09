package ru.droidcat.feature.auth.internal.ui.register

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.auth.api.ui.register.model.RegisterState
import ru.droidcat.feature.auth.internal.ui.register.model.Intent
import ru.droidcat.feature.auth.internal.ui.register.model.Label

private const val STORE_NAME = "RegisterStore"

internal class DefaultRegisterStore(
    storeFactory: StoreFactory,
    executor: DefaultRegisterExecutor,
    reducer: DefaultRegisterReducer,
) : Store<Intent, RegisterState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = RegisterState(),
    executorFactory = { executor },
    reducer = reducer,
)

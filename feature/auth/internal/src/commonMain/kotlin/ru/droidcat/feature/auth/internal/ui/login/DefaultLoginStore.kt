package ru.droidcat.feature.auth.internal.ui.login

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import ru.droidcat.feature.auth.api.ui.login.model.LoginState
import ru.droidcat.feature.auth.internal.ui.login.model.Intent
import ru.droidcat.feature.auth.internal.ui.login.model.Label

private const val STORE_NAME = "LoginStore"

internal class DefaultLoginStore(
    storeFactory: StoreFactory,
    executor: DefaultLoginExecutor,
    reducer: DefaultLoginReducer,
) : Store<Intent, LoginState, Label> by storeFactory.create(
    name = STORE_NAME,
    initialState = LoginState(),
    executorFactory = { executor },
    reducer = reducer,
)

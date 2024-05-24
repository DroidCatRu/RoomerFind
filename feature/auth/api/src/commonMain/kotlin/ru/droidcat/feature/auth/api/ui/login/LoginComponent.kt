package ru.droidcat.feature.auth.api.ui.login

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.auth.api.ui.login.model.LoginIntent
import ru.droidcat.feature.auth.api.ui.login.model.LoginState

interface LoginComponent : IntentAcceptor<LoginIntent> {

    val viewState: StateFlow<LoginState>
}

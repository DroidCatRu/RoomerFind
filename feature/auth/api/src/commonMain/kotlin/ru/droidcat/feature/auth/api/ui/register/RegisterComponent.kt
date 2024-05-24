package ru.droidcat.feature.auth.api.ui.register

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.auth.api.ui.register.model.RegisterIntent
import ru.droidcat.feature.auth.api.ui.register.model.RegisterState

interface RegisterComponent : IntentAcceptor<RegisterIntent> {

    val viewState: StateFlow<RegisterState>
}

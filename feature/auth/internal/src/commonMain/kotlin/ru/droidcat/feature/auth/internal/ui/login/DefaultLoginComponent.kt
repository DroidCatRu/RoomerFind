package ru.droidcat.feature.auth.internal.ui.login

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.auth.api.ui.login.LoginComponent
import ru.droidcat.feature.auth.api.ui.login.model.LoginIntent
import ru.droidcat.feature.auth.api.ui.login.model.LoginState
import ru.droidcat.feature.auth.internal.ui.login.model.Label

internal class DefaultLoginComponent(
    componentContext: ComponentContext,
    private val onRegister: () -> Unit,
) : LoginComponent, BaseComponentWithStore<LoginIntent, LoginState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultLoginStore>() }
) {

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is Label.RegisterRequest -> onRegister()
        }
    }
}

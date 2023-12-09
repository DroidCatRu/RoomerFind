package ru.droidcat.feature.auth.internal.ui.login

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.auth.api.ui.login.LoginComponent
import ru.droidcat.feature.auth.api.ui.login.model.LoginState
import ru.droidcat.feature.auth.internal.ui.login.model.Intent
import ru.droidcat.feature.auth.internal.ui.login.model.Intent.OnConfirm
import ru.droidcat.feature.auth.internal.ui.login.model.Intent.OnLoginChange
import ru.droidcat.feature.auth.internal.ui.login.model.Intent.OnPasswordChange
import ru.droidcat.feature.auth.internal.ui.login.model.Label

internal class DefaultLoginComponent(
    componentContext: ComponentContext,
    private val onRegister: () -> Unit,
) : LoginComponent, BaseComponentWithStore<Intent, LoginState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultLoginStore>() }
) {

    override fun onLoginChange(login: String) {
        accept(OnLoginChange(login))
    }

    override fun onPasswordChange(password: String) {
        accept(OnPasswordChange(password))
    }

    override fun onConfirm() {
        accept(OnConfirm)
    }

    override fun onRegisterRequest() {
        onRegister()
    }
}

fun createLoginComponent(
    componentContext: ComponentContext,
    onRegister: () -> Unit = {},
): LoginComponent = DefaultLoginComponent(
    componentContext = componentContext,
    onRegister = onRegister,
)

package ru.droidcat.feature.auth.internal.ui.register

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.auth.api.ui.register.RegisterComponent
import ru.droidcat.feature.auth.api.ui.register.model.RegisterState
import ru.droidcat.feature.auth.internal.ui.register.model.Intent
import ru.droidcat.feature.auth.internal.ui.register.model.Intent.OnConfirm
import ru.droidcat.feature.auth.internal.ui.register.model.Intent.OnLoginChange
import ru.droidcat.feature.auth.internal.ui.register.model.Intent.OnPasswordChange
import ru.droidcat.feature.auth.internal.ui.register.model.Label

internal class DefaultRegisterComponent(
    componentContext: ComponentContext,
    private val onLogin: () -> Unit,
) : RegisterComponent, BaseComponentWithStore<Intent, RegisterState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultRegisterStore>() },
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

    override fun onLoginRequest() {
        onLogin()
    }
}

fun createRegisterComponent(
    componentContext: ComponentContext,
    onLogin: () -> Unit = {},
): RegisterComponent = DefaultRegisterComponent(
    componentContext = componentContext,
    onLogin = onLogin,
)

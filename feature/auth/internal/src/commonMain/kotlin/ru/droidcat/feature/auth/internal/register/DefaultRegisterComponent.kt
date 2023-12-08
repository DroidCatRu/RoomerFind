package ru.droidcat.feature.auth.internal.register

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.auth.api.register.RegisterComponent

private class DefaultRegisterComponent(
    componentContext: ComponentContext,
    private val onLogin: () -> Unit,
) : RegisterComponent, BaseComponent(
    componentContext = componentContext,
) {

    override fun onLoginChange() {
        TODO("Not yet implemented")
    }

    override fun onPasswordChange() {
        TODO("Not yet implemented")
    }

    override fun onConfirm() {
        TODO("Not yet implemented")
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

package ru.droidcat.feature.auth.internal.login

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.auth.api.login.LoginComponent

private class DefaultLoginComponent(
    componentContext: ComponentContext,
    private val onRegister: () -> Unit,
) : LoginComponent, BaseComponent(
    componentContext = componentContext,
) {

    override fun onLoginChange(login: String) {
        TODO("Not yet implemented")
    }

    override fun onPasswordChange(password: String) {
        TODO("Not yet implemented")
    }

    override fun onConfirm() {
        TODO("Not yet implemented")
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

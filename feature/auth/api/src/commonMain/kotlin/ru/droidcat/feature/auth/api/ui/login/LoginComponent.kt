package ru.droidcat.feature.auth.api.ui.login

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.auth.api.ui.login.model.LoginState

interface LoginComponent {

    val viewState: Value<LoginState>

    fun onLoginChange(login: String)

    fun onPasswordChange(password: String)

    fun onConfirm()

    fun onRegisterRequest()
}

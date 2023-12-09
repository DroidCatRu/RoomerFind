package ru.droidcat.feature.auth.api.ui.register

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.auth.api.ui.register.model.RegisterState

interface RegisterComponent {

    val viewState: Value<RegisterState>

    fun onLoginChange(login: String)

    fun onPasswordChange(password: String)

    fun onConfirm()

    fun onLoginRequest()
}

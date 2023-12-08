package ru.droidcat.feature.auth.api.root.model

import ru.droidcat.feature.auth.api.login.LoginComponent
import ru.droidcat.feature.auth.api.register.RegisterComponent

sealed interface AuthChild {

    data class LoginChild(val component: LoginComponent) : AuthChild

    data class RegisterChild(val component: RegisterComponent) : AuthChild
}

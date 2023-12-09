package ru.droidcat.feature.auth.api.ui.root.model

import ru.droidcat.feature.auth.api.ui.login.LoginComponent
import ru.droidcat.feature.auth.api.ui.register.RegisterComponent

sealed interface AuthChild {

    data class LoginChild(val component: LoginComponent) : AuthChild

    data class RegisterChild(val component: RegisterComponent) : AuthChild
}

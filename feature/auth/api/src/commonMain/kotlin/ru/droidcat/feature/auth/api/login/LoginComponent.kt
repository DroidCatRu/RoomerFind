package ru.droidcat.feature.auth.api.login

interface LoginComponent {

    fun onLoginChange(login: String)

    fun onPasswordChange(password: String)

    fun onConfirm()

    fun onRegisterRequest()
}

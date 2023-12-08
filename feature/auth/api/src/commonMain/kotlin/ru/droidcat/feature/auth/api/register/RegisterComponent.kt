package ru.droidcat.feature.auth.api.register

interface RegisterComponent {

    fun onLoginChange()

    fun onPasswordChange()

    fun onConfirm()

    fun onLoginRequest()
}

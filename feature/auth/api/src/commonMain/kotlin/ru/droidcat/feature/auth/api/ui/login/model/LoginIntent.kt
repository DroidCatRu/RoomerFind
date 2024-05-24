package ru.droidcat.feature.auth.api.ui.login.model

sealed interface LoginIntent {

    data class OnEmailChange(val value: String) : LoginIntent

    data class OnPasswordChange(val value: String) : LoginIntent

    data object OnConfirm : LoginIntent

    data object OnRegister : LoginIntent
}

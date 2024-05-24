package ru.droidcat.feature.auth.api.ui.register.model

interface RegisterIntent {

    data class OnEmailChange(val value: String) : RegisterIntent

    data class OnPasswordChange(val value: String) : RegisterIntent

    data object OnConfirm : RegisterIntent

    data object OnLogin : RegisterIntent
}

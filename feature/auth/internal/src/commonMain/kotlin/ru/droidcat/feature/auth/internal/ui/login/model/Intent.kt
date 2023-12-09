package ru.droidcat.feature.auth.internal.ui.login.model

internal sealed interface Intent {

    data class OnLoginChange(val login: String) : Intent

    data class OnPasswordChange(val password: String) : Intent

    data object OnConfirm : Intent
}

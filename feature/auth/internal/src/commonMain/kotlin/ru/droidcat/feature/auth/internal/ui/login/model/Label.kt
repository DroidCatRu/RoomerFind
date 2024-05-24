package ru.droidcat.feature.auth.internal.ui.login.model

internal sealed interface Label {

    data object RegisterRequest : Label
}

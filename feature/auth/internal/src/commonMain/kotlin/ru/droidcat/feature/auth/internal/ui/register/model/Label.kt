package ru.droidcat.feature.auth.internal.ui.register.model

internal sealed interface Label {

    data object LoginRequest : Label
}

package ru.droidcat.feature.auth.internal.ui.register.model

internal sealed interface Message {

    data class SetLogin(
        val value: String,
    ) : Message

    data class SetPassword(
        val value: String,
    ) : Message
}

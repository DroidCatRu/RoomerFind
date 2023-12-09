package ru.droidcat.feature.auth.internal.ui.register.model

internal sealed interface Message {

    data class SetLogin(val login: String) : Message

    data class SetPassword(val password: String) : Message
}

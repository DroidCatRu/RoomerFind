package ru.droidcat.feature.auth.api.model

data class Token(
    val value: String,
)

object NoToken : Throwable("NoToken")

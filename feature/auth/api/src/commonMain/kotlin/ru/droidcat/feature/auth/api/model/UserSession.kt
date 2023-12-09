package ru.droidcat.feature.auth.api.model

sealed interface UserSession {
    data class Defined(
        val sessionId: String,
    ) : UserSession

    data object Empty : UserSession

    data object Unknown : UserSession
}

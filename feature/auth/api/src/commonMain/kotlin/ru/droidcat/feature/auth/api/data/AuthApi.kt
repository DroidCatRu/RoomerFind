package ru.droidcat.feature.auth.api.data

import ru.droidcat.feature.auth.api.model.UserSession

interface AuthApi {

    suspend fun login(
        login: String,
        password: String,
    ): UserSession.Defined

    suspend fun register(
        login: String,
        password: String,
    ): UserSession.Defined
}

package ru.droidcat.feature.auth.api.data

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.feature.auth.api.model.UserSession

interface AuthDB {

    val userSession: StateFlow<UserSession>

    suspend fun saveSession(session: UserSession.Defined)

    suspend fun logOut()
}

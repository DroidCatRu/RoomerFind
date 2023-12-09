package ru.droidcat.feature.auth.internal.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import ru.droidcat.core.mvi.ioDispatcher
import ru.droidcat.feature.auth.api.data.AuthDB
import ru.droidcat.feature.auth.api.model.UserSession
import ru.droidcat.feature.auth.api.model.UserSession.Defined

internal class DefaultAuthDB : AuthDB {

    private val _userSession = MutableStateFlow<UserSession>(UserSession.Empty)

    override val userSession: StateFlow<UserSession> = _userSession

    override suspend fun saveSession(session: Defined) {
        withContext(ioDispatcher) {
            _userSession.emit(session)
        }
    }

    override suspend fun logOut() {
        withContext(ioDispatcher) {
            _userSession.emit(UserSession.Empty)
        }
    }
}

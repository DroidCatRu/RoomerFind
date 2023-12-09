package ru.droidcat.feature.finders.api.usecase

import ru.droidcat.core.mvi.mapSuspendCatching
import ru.droidcat.core.mvi.runSuspendCatching
import ru.droidcat.feature.auth.api.data.AuthDB
import ru.droidcat.feature.auth.api.model.UserSession.Defined
import ru.droidcat.feature.finders.api.data.FindersApi

class GetMatchesUseCase(
    private val api: FindersApi,
    private val authDB: AuthDB,
) {

    suspend operator fun invoke() = runSuspendCatching {
        authDB.userSession.value as Defined
    }
        .mapSuspendCatching { session ->
            api.getMatches(
                session = session,
            )
        }
        .onSuccess { println(it) }
        .onFailure { println(it) }
}

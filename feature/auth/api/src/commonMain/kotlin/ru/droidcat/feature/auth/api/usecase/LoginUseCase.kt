package ru.droidcat.feature.auth.api.usecase

import ru.droidcat.core.mvi.mapSuspendCatching
import ru.droidcat.core.mvi.runSuspendCatching
import ru.droidcat.feature.auth.api.data.AuthApi
import ru.droidcat.feature.auth.api.data.AuthDB

class LoginUseCase(
    private val api: AuthApi,
    private val db: AuthDB,
) {

    suspend operator fun invoke(
        login: String,
        password: String,
    ) = runSuspendCatching {
        api.login(
            login = login,
            password = password,
        )
    }
        .mapSuspendCatching { session ->
            db.saveSession(
                session = session,
            )
        }
}

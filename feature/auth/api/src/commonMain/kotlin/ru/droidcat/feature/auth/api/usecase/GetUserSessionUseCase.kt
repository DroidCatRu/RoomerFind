package ru.droidcat.feature.auth.api.usecase

import kotlinx.coroutines.flow.onEmpty
import ru.droidcat.feature.auth.api.data.AuthDB
import ru.droidcat.feature.auth.api.model.UserSession

class GetUserSessionUseCase(
    private val db: AuthDB,
) {

    operator fun invoke() = db.userSession
        .onEmpty {
            emit(UserSession.Unknown)
        }
}

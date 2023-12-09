package ru.droidcat.feature.profile.api.usecase

import kotlinx.coroutines.withContext
import ru.droidcat.core.mvi.ioDispatcher
import ru.droidcat.core.mvi.mapSuspendCatching
import ru.droidcat.core.mvi.runSuspendCatching
import ru.droidcat.feature.auth.api.data.AuthDB
import ru.droidcat.feature.auth.api.model.UserSession.Defined
import ru.droidcat.feature.profile.api.data.ProfileApi
import ru.droidcat.feature.profile.api.model.UserProfile

class GetProfileUseCase(
    private val api: ProfileApi,
    private val authDB: AuthDB,
) {

    suspend operator fun invoke() = runSuspendCatching {
        withContext(ioDispatcher) {
            authDB.userSession.value as Defined
        }
    }
        .mapSuspendCatching { session ->
            api.getProfile(
                session = session,
            )
        }
        .onSuccess { println(it) }
        .onFailure { println(it) }
        .fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.success(UserProfile.Defined()) },
        )
}

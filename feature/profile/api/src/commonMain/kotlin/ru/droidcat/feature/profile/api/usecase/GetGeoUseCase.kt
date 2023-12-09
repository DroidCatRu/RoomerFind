package ru.droidcat.feature.profile.api.usecase

import ru.droidcat.core.mvi.mapSuspendCatching
import ru.droidcat.core.mvi.runSuspendCatching
import ru.droidcat.feature.auth.api.data.AuthDB
import ru.droidcat.feature.auth.api.model.UserSession.Defined
import ru.droidcat.feature.profile.api.data.ProfileApi
import ru.droidcat.feature.profile.api.model.Geo

class GetGeoUseCase(
    private val api: ProfileApi,
    private val authDB: AuthDB,
) {

    suspend operator fun invoke() = runSuspendCatching {
        authDB.userSession.value as Defined
    }
        .mapSuspendCatching { session ->
            api.getGeo(
                session = session,
            )
        }
        .onSuccess { println(it) }
        .onFailure { println(it) }
        .fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.success(Geo.Defined()) },
        )
}

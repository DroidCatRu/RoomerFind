package ru.droidcat.feature.profile.api.usecase

import ru.droidcat.core.mvi.mapSuspendCatching
import ru.droidcat.core.mvi.runSuspendCatching
import ru.droidcat.feature.auth.api.data.AuthDB
import ru.droidcat.feature.auth.api.model.UserSession.Defined
import ru.droidcat.feature.profile.api.data.ProfileApi
import ru.droidcat.feature.profile.api.model.UserPreference

class SavePreferenceUseCase(
    private val api: ProfileApi,
    private val authDB: AuthDB,
) {

    suspend operator fun invoke(
        preference: UserPreference.Defined,
    ) = runSuspendCatching {
        authDB.userSession.value as Defined
    }
        .mapSuspendCatching { session ->
            api.savePreference(
                session = session,
                preference = preference,
            )
        }
        .onSuccess { println(it) }
        .onFailure { println(it) }
}

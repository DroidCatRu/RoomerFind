package ru.droidcat.feature.profile.internal.data

import kotlinx.coroutines.flow.Flow
import ru.droidcat.core.mvi.runSuspendCatching
import ru.droidcat.core.preferences.Preferences
import ru.droidcat.core.preferences.Preferences.TypedKey
import ru.droidcat.core.preferences.getTypedOrNullFlow
import ru.droidcat.core.preferences.putTyped
import ru.droidcat.feature.profile.api.data.LocalProfileApi
import ru.droidcat.roomerfind.model.network.UserInfoDTO

class LocalProfileImpl(
    private val preferences: Preferences,
) : LocalProfileApi {

    override suspend fun saveProfile(profile: UserInfoDTO) = runSuspendCatching {
        preferences.putTyped(UserProfile, profile)
    }

    override suspend fun clearProfile() {
        preferences.remove(UserProfile)
    }

    override val profile: Flow<UserInfoDTO?> =
        preferences.getTypedOrNullFlow(UserProfile)
}

private val UserProfile = TypedKey<UserInfoDTO>("USER_PROFILE")

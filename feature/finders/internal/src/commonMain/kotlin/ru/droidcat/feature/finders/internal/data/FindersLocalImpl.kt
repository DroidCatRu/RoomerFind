package ru.droidcat.feature.finders.internal.data

import kotlinx.coroutines.flow.Flow
import ru.droidcat.core.preferences.Preferences
import ru.droidcat.core.preferences.Preferences.TypedKey
import ru.droidcat.core.preferences.getTypedFlow
import ru.droidcat.core.preferences.getTypedOrNullFlow
import ru.droidcat.core.preferences.putTyped
import ru.droidcat.feature.finders.api.data.FindersLocalApi
import ru.droidcat.roomerfind.model.network.UserInfoDTO

class FindersLocalImpl(
    private val preferences: Preferences,
) : FindersLocalApi {

    override suspend fun saveMatches(matches: List<UserInfoDTO>) {
        preferences.putTyped(PreferenceMatches, matches)
    }

    override suspend fun clearAllMatches() {
        preferences.remove(PreferenceMatches)
    }

    override val matches: Flow<List<UserInfoDTO>> =
        preferences.getTypedFlow(PreferenceMatches, emptyList())

    override suspend fun saveLastInfo(info: UserInfoDTO) {
        preferences.putTyped(PreferenceLastMatch, info)
    }

    override suspend fun clearLastInfo() {
        preferences.remove(PreferenceLastMatch)
    }

    override val lastInfo: Flow<UserInfoDTO?> =
        preferences.getTypedOrNullFlow(PreferenceLastMatch)
}

private val PreferenceMatches = TypedKey<List<UserInfoDTO>>("MATCHES_LIST")
private val PreferenceLastMatch = TypedKey<UserInfoDTO>("LAST_MATCH")

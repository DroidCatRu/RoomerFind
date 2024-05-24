package ru.droidcat.feature.auth.internal.data

import kotlinx.coroutines.flow.Flow
import ru.droidcat.core.preferences.Preferences
import ru.droidcat.feature.auth.api.data.AuthLocalApi
import ru.droidcat.feature.auth.api.model.Token

class AuthLocalImpl(
    private val preferences: Preferences,
) : AuthLocalApi {

    override suspend fun saveToken(token: Token) {
        preferences.putString(PreferenceToken, token.value)
    }

    override suspend fun clearToken() {
        preferences.remove(PreferenceToken)
    }

    override val token: Flow<String?> =
        preferences.getStringOrNullFlow(PreferenceToken)
}

private val PreferenceToken = Preferences.StringKey("AUTH_TOKEN")
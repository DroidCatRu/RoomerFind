package ru.droidcat.feature.auth.api.data

import kotlinx.coroutines.flow.Flow
import ru.droidcat.feature.auth.api.model.Token

interface AuthLocalApi {

    /**
     * Saves the authentication token.
     *
     * @param token The token to save.
     */
    suspend fun saveToken(token: Token)

    suspend fun clearToken()

    /**
     * A flow representing the authentication token.
     *
     * @return The authentication token as a flow of nullable strings.
     */
    val token: Flow<String?>
}

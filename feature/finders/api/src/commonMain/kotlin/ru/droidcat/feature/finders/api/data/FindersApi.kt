package ru.droidcat.feature.finders.api.data

import ru.droidcat.feature.auth.api.model.UserSession
import ru.droidcat.feature.finders.api.model.SuggestionInfo

interface FindersApi {

    suspend fun getMatches(
        session: UserSession.Defined,
    ): List<SuggestionInfo.Defined>

    suspend fun getSuggestions(
        session: UserSession.Defined,
    ): List<SuggestionInfo.Defined>

    suspend fun getSuggestionInfo(
        session: UserSession.Defined,
        suggestionId: Int,
    ): SuggestionInfo.Defined

    suspend fun like(
        session: UserSession.Defined,
        suggestionId: Int,
    )
}

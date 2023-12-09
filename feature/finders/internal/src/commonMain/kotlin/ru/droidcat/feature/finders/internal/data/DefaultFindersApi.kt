package ru.droidcat.feature.finders.internal.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel.ALL
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.droidcat.core.mvi.ioDispatcher
import ru.droidcat.feature.auth.api.model.UserSession
import ru.droidcat.feature.finders.api.data.FindersApi
import ru.droidcat.feature.finders.api.model.SuggestionInfo.Defined

private const val BASE_URL = "http://31.129.42.155:8080"

private const val GET_MATCHES_ENDPOINT = "matches"
private const val GET_SUGGEST_ENDPOINT = ""
private const val LIKE_ENDPOINT = "like"

class DefaultFindersApi : FindersApi {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val httpClient = HttpClient(
        engineFactory = platformEngineFactory,
    ) {
        expectSuccess = true

        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    override suspend fun getMatches(session: UserSession.Defined): List<Defined> {
        return withContext(ioDispatcher) {
            val matches = httpClient.get {
                bearerAuth(session.sessionId)
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(GET_MATCHES_ENDPOINT)
                }
            }.body<MatchesDTO>().content
            matches.map {
                Defined(
                    id = it.key,
                    name = it.value.name,
                    age = it.value.age.toString(),
                    description = it.value.description,
                )
            }
        }
    }

    override suspend fun getSuggestions(session: UserSession.Defined): List<Defined> {
        TODO("Not yet implemented")
    }

    override suspend fun getSuggestionInfo(session: UserSession.Defined, suggestionId: Int): Defined {
        TODO("Not yet implemented")
    }

    override suspend fun like(session: UserSession.Defined, suggestionId: Int) {
        TODO("Not yet implemented")
    }
}

@Serializable
data class MatchesDTO(
    val content: Map<Int, MatchInfoDTO>
)

@Serializable
data class MatchInfoDTO(
    val name: String = "",
    val age: Int? = null,
    val description: String = "",
    val gender: Int = 0,
)

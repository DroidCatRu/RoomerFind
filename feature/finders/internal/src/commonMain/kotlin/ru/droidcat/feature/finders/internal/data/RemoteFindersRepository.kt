package ru.droidcat.feature.finders.internal.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel.ALL
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.droidcat.core.coroutines.requestWrapper
import ru.droidcat.feature.auth.api.usecase.TokenUseCase
import ru.droidcat.roomerfind.model.network.DISLIKE_ENDPOINT
import ru.droidcat.roomerfind.model.network.FINDER_ENDPOINT
import ru.droidcat.roomerfind.model.network.LIKE_ENDPOINT
import ru.droidcat.roomerfind.model.network.MATCHES_ENDPOINT
import ru.droidcat.roomerfind.model.network.MatchesDTO
import ru.droidcat.roomerfind.model.network.USER_INFO_ENDPOINT
import ru.droidcat.roomerfind.model.network.UrlProvider
import ru.droidcat.roomerfind.model.network.UserInfoDTO

class RemoteFindersRepository(
    private val tokenUseCase: TokenUseCase,
    private val urlProvider: UrlProvider,
) {

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

    suspend fun getMatches(): Result<MatchesDTO> = requestWrapper {
        httpClient.get {
            bearerAuth(tokenUseCase.getTokenForRequest())
            url {
                takeFrom(urlProvider.getBasePath())
                appendPathSegments(MATCHES_ENDPOINT)
            }
        }
    }

    suspend fun getNextSuggestion(): Result<UserInfoDTO> = requestWrapper {
        httpClient.get {
            bearerAuth(tokenUseCase.getTokenForRequest())
            url {
                takeFrom(urlProvider.getBasePath())
                appendPathSegments(FINDER_ENDPOINT)
            }
        }
    }

    suspend fun like(): Result<Unit> = requestWrapper {
        httpClient.post {
            bearerAuth(tokenUseCase.getTokenForRequest())
            url {
                takeFrom(urlProvider.getBasePath())
                appendPathSegments(LIKE_ENDPOINT)
            }
        }
    }

    suspend fun dislike(): Result<Unit> = requestWrapper {
        httpClient.post {
            bearerAuth(tokenUseCase.getTokenForRequest())
            url {
                takeFrom(urlProvider.getBasePath())
                appendPathSegments(DISLIKE_ENDPOINT)
            }
        }
    }

    suspend fun getProfileById(id: Long): Result<UserInfoDTO> = requestWrapper {
        httpClient.get {
            bearerAuth(tokenUseCase.getTokenForRequest())
            url {
                takeFrom(urlProvider.getBasePath())
                appendPathSegments(USER_INFO_ENDPOINT)
                appendPathSegments(id.toString())
            }
        }
    }
}

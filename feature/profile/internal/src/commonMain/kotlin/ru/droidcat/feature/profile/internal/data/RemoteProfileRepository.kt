package ru.droidcat.feature.profile.internal.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.droidcat.core.coroutines.requestWrapper
import ru.droidcat.feature.auth.api.usecase.TokenUseCase
import ru.droidcat.roomerfind.model.network.USER_INFO_ENDPOINT
import ru.droidcat.roomerfind.model.network.UrlProvider
import ru.droidcat.roomerfind.model.network.UserInfoDTO

class RemoteProfileRepository(
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
            level = LogLevel.ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    suspend fun getProfile(): Result<UserInfoDTO> = requestWrapper {
        httpClient.get {
            bearerAuth(tokenUseCase.getTokenForRequest())
            url {
                takeFrom(urlProvider.getBasePath())
                appendPathSegments(USER_INFO_ENDPOINT)
            }
        }
    }

    suspend fun saveProfile(profile: UserInfoDTO): Result<Unit> = requestWrapper {
        httpClient.put {
            bearerAuth(tokenUseCase.getTokenForRequest())
            url {
                takeFrom(urlProvider.getBasePath())
                appendPathSegments(USER_INFO_ENDPOINT)
            }
            contentType(ContentType.Application.Json)
            setBody(profile)
        }
    }
}

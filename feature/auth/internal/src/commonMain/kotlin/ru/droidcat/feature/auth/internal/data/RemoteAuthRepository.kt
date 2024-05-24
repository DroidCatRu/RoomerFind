package ru.droidcat.feature.auth.internal.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.droidcat.core.coroutines.requestWrapper
import ru.droidcat.roomerfind.model.network.LOGIN_ENDPOINT
import ru.droidcat.roomerfind.model.network.REGISTER_ENDPOINT
import ru.droidcat.roomerfind.model.network.UrlProvider
import ru.droidcat.roomerfind.model.network.UserAuthDTO
import ru.droidcat.roomerfind.model.network.UserCredDTO

class RemoteAuthRepository(
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

    suspend fun login(
        email: String,
        password: String,
    ): Result<UserCredDTO> = auth(
        email = email,
        password = password,
        authPath = LOGIN_ENDPOINT,
    )

    suspend fun register(
        email: String,
        password: String,
    ): Result<UserCredDTO> = auth(
        email = email,
        password = password,
        authPath = REGISTER_ENDPOINT,
    )

    private suspend fun auth(
        email: String,
        password: String,
        authPath: String,
    ): Result<UserCredDTO> = requestWrapper {
        httpClient.post {
            url {
                takeFrom(urlProvider.getBasePath())
                appendPathSegments(authPath)
            }
            contentType(ContentType.Application.Json)
            setBody(
                UserAuthDTO(
                    email = email,
                    password = password,
                )
            )
        }
    }
}

package ru.droidcat.feature.auth.internal.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.droidcat.core.mvi.ioDispatcher
import ru.droidcat.feature.auth.api.data.AuthApi
import ru.droidcat.feature.auth.api.model.UserSession.Defined

private const val BASE_URL = "http://31.129.42.155:8080"
private const val LOGIN_ENDPOINT = "login"
private const val REGISTER_ENDPOINT = "register"

internal class DefaultAuthApi : AuthApi {

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

    override suspend fun login(
        login: String,
        password: String,
    ): Defined = auth(
        login = login,
        password = password,
        authPath = LOGIN_ENDPOINT,
    )

    override suspend fun register(
        login: String,
        password: String,
    ): Defined = auth(
        login = login,
        password = password,
        authPath = REGISTER_ENDPOINT,
    )

    private suspend fun auth(
        login: String,
        password: String,
        authPath: String,
    ): Defined {
        println("auth")
        return withContext(ioDispatcher) {
            val token = httpClient.post {
                url {
                    takeFrom(BASE_URL)
                    appendPathSegments(authPath)
                }
                contentType(ContentType.Application.Json)
                setBody(
                    AuthData(
                        login = login,
                        password = password,
                    )
                )
            }
                .body<SessionToken>()
                .token

            println("auth success")

            Defined(
                sessionId = token,
            )
        }
    }
}

@Serializable
data class AuthData(
    @SerialName("login") val login: String,
    @SerialName("password") val password: String,
)

@Serializable
data class SessionToken(
    @SerialName("token") val token: String,
)

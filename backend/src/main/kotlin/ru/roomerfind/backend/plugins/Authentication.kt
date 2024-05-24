package ru.roomerfind.backend.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.response.respond
import org.koin.ktor.ext.inject
import ru.roomerfind.backend.utils.TokenManager

fun Application.installAuthentication() {
    val tokenManager by inject<TokenManager>()
    val config by inject<ApplicationConfig>()

    authentication {
        jwt {
            val jwtAudience = config.property("jwt.audience").getString()
            realm = config.property("jwt.realm").getString()
            verifier(
                tokenManager.verifyJWTToken()
            )
            validate { credential ->
                credential.payload
                    .takeIf { it.audience.contains(jwtAudience) }
                    ?.let { JWTPrincipal(it) }
            }
            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = "Token is not valid or has expired",
                )
            }
        }
    }
}

package ru.roomerfind.backend.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.ApplicationConfig
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class TokenManager(config: ApplicationConfig) {

    private val secret = config.property("jwt.secret").getString()
    private val issuer = config.property("jwt.issuer").getString()
    private val audience = config.property("jwt.audience").getString()
    private val expirationDate: LocalDate = LocalDate.now().plusWeeks(2)

    fun generateJWTToken(
        id: Long,
        email: String,
    ): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("id", id)
            .withClaim("email", email)
            .withExpiresAt(Date.from(expirationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
            .sign(Algorithm.HMAC256(secret))
    }

    fun verifyJWTToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }
}

package ru.droidcat.roomerfind.server.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*

import io.ktor.server.config.*
import com.typesafe.config.ConfigFactory
import ru.droidcat.roomerfind.server.database.tokens.Tokens

fun Application.configureAuthentication() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    install(Authentication) {
        bearer("auth-bearer") {
            realm = config.property("REALM").getString()
            authenticate { bearerTokenCredential ->
                if (Tokens.fetchTokens().any{ it.token == bearerTokenCredential.token}){
                    UserIdPrincipal("id")
                } else {
                    null
                }
            }
        }
    }
}
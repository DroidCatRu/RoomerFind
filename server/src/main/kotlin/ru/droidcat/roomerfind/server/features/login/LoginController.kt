package ru.droidcat.roomerfind.server.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.mindrot.jbcrypt.BCrypt
import ru.droidcat.roomerfind.server.database.tokens.TokenDTO
import ru.droidcat.roomerfind.server.database.tokens.Tokens
import ru.droidcat.roomerfind.server.database.user_credentials.UserCredentials
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()

        val userCreds = UserCredentials.getCredentialsByLogin(receive.login)

        if (userCreds == null) {
            call.respond(HttpStatusCode.Conflict, "Invalid login or password")
        } else {
            val passwordMatches = BCrypt.checkpw(receive.password, userCreds.password)
            if (passwordMatches) {
                val token = UUID.randomUUID().toString()
                Tokens.insertToken (
                    TokenDTO(
                        credentialId = UserCredentials.getIdByLogin(receive.login),
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid login or password")
            }
        }
    }
}
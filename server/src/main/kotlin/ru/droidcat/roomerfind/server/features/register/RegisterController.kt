package ru.droidcat.roomerfind.server.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.droidcat.roomerfind.server.database.tokens.TokenDTO
import ru.droidcat.roomerfind.server.database.tokens.Tokens
import ru.droidcat.roomerfind.server.database.user_credentials.UserCredentials
import ru.droidcat.roomerfind.server.database.user_credentials.UserCredentialsDTO
import ru.droidcat.roomerfind.server.database.user_info.UserInfo
import ru.droidcat.roomerfind.server.database.user_info.UserInfoDTO
import ru.droidcat.roomerfind.server.utils.hashPassword
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    /**
     * Performs user registration via login and password
     *
     * input: login, password
     *
     * returns: access token
     **/
    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()

        val userCreds = UserCredentials.getCredentialsByLogin(registerReceiveRemote.login)
        if (userCreds != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {

            try {
                val credRowId = UserCredentials.insertCredentials(
                    UserCredentialsDTO(
                        userId = -1,
                        login = registerReceiveRemote.login,
                        password = hashPassword( registerReceiveRemote.password)
                    )
                )

                val userRowId = UserInfo.insertUserInfo(
                    UserInfoDTO(
                        name = "",
                        age = -1,
                        description = "",
                        gender = -1,

                        photo = null,
                        credentials = credRowId,
                        preferences = null,
                        contacts = null
                    )
                )

                UserCredentials.updateUserIdByLogin(registerReceiveRemote.login, userRowId)

                val token = UUID.randomUUID().toString()
                Tokens.insertToken(
                    TokenDTO(
                        credentialId = credRowId,
                        token = token
                    )
                )

                call.respond(RegisterResponseRemote(token = token))
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Can't create user ${e.localizedMessage}")
            }
        }
    }
}
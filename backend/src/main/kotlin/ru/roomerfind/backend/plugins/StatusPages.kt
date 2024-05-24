package ru.roomerfind.backend.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import ru.droidcat.roomerfind.model.network.StatusCode
import ru.droidcat.roomerfind.model.network.StatusCode.Companion.UserAlreadyExists

fun Application.installStatusPages() {
    install(StatusPages) {
        exception<UserAlreadyExistsException> { call, _ ->
            call.respond(UserAlreadyExists.httpStatusCode, "User already exists")
        }
        exception<InvalidEmailException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, "Invalid email")
        }
        exception<InvalidPasswordException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, "Invalid password")
        }
        exception<UserNotFoundException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, "User not found")
        }
        exception<ResourceNotFoundException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, "Resource not found")
        }
        exception<BadParametersException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, "Check request parameters")
        }
        exception<NotAuthorizedException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden, "Not authorized")
        }
        exception<WrongFormatException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, "Wrong format")
        }
    }
}

class UserAlreadyExistsException : RuntimeException()
class InvalidEmailException : RuntimeException()
class InvalidPasswordException : RuntimeException()
class UserNotFoundException : RuntimeException()
class ResourceNotFoundException : RuntimeException()
class BadParametersException : RuntimeException()
class NotAuthorizedException : RuntimeException()
class WrongFormatException : RuntimeException()

private val StatusCode.httpStatusCode: HttpStatusCode get() = HttpStatusCode(
    value = value,
    description = description,
)

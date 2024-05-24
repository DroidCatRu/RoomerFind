package ru.roomerfind.backend.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.koin.ktor.ext.inject
import ru.droidcat.roomerfind.model.network.LOGIN_ENDPOINT
import ru.droidcat.roomerfind.model.network.REGISTER_ENDPOINT
import ru.droidcat.roomerfind.model.network.UserAuthDTO
import ru.roomerfind.backend.controller.AuthController

fun Route.authRoutes() {
    val authController by inject<AuthController>()

    post(REGISTER_ENDPOINT) {
        val userCredentials = call.receive<UserAuthDTO>()
        call.respond(HttpStatusCode.OK, authController.register(userCredentials))
    }

    post(LOGIN_ENDPOINT) {
        val userCredentials = call.receive<UserAuthDTO>()
        call.respond(HttpStatusCode.OK, authController.login(userCredentials))
    }
}

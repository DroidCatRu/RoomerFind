package ru.roomerfind.backend.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import org.koin.ktor.ext.inject
import ru.droidcat.roomerfind.model.network.USER_INFO_ENDPOINT
import ru.droidcat.roomerfind.model.network.UserInfoDTO
import ru.roomerfind.backend.controller.UserInfoController
import ru.roomerfind.backend.utils.getUserId

fun Route.userInfoRoutes() {
    val userInfoController by inject<UserInfoController>()

    authenticate {
        put(USER_INFO_ENDPOINT) {
            val userId = getUserId(call.principal())
            val info = call.receive<UserInfoDTO>()
            userInfoController.updateInfo(userId, info)
            call.respond(HttpStatusCode.OK)
        }

        get("$USER_INFO_ENDPOINT/{userId?}") {
            val currentId = getUserId(call.principal())
            val userId: Long = call.parameters["userId"]?.toLongOrNull() ?: currentId
            call.respond(HttpStatusCode.OK, userInfoController.getInfo(userId, currentId))
        }
    }
}

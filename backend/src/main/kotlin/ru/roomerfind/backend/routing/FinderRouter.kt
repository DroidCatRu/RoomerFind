package ru.roomerfind.backend.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.koin.ktor.ext.inject
import ru.droidcat.roomerfind.model.network.DISLIKE_ENDPOINT
import ru.droidcat.roomerfind.model.network.FINDER_ENDPOINT
import ru.droidcat.roomerfind.model.network.LIKE_ENDPOINT
import ru.droidcat.roomerfind.model.network.MATCHES_ENDPOINT
import ru.roomerfind.backend.controller.FinderController
import ru.roomerfind.backend.utils.getUserId

fun Route.finderRoutes() {
    val finderController by inject<FinderController>()

    authenticate {
        get(FINDER_ENDPOINT) {
            val userId = getUserId(call.principal())
            call.respond(HttpStatusCode.OK, finderController.getNextFinderForUser(userId))
        }

        post(LIKE_ENDPOINT) {
            val userId = getUserId(call.principal())
            finderController.likeLast(userId)
            call.respond(HttpStatusCode.OK)
        }

        post(DISLIKE_ENDPOINT) {
            val userId = getUserId(call.principal())
            finderController.dislikeLast(userId)
            call.respond(HttpStatusCode.OK)
        }

        get(MATCHES_ENDPOINT) {
            val userId = getUserId(call.principal())
            call.respond(HttpStatusCode.OK, finderController.getMatches(userId))
        }
    }
}

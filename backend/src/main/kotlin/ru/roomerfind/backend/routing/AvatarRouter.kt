package ru.roomerfind.backend.routing

import io.ktor.http.ContentDisposition
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.contentType
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.util.toByteArray
import org.koin.ktor.ext.inject
import ru.droidcat.roomerfind.model.network.AVATAR_ENDPOINT
import ru.roomerfind.backend.controller.AvatarController
import ru.roomerfind.backend.plugins.BadParametersException
import ru.roomerfind.backend.utils.getUserId

fun Route.avatarRoutes() {
    val avatarController by inject<AvatarController>()

    get("$AVATAR_ENDPOINT/{filename}") {
        val file = avatarController.getAvatar(call.parameters["filename"])
        call.response.header(
            HttpHeaders.ContentDisposition,
            ContentDisposition.Inline.withParameter(
                ContentDisposition.Parameters.FileName,
                file.name,
            ).toString()
        )
        call.respondFile(file)
    }

    authenticate {
        put(AVATAR_ENDPOINT) {
            val userId = getUserId(call.principal())
            val contentType = call.request.contentType()
            when {
                contentType.match(ContentType.Image.Any) -> avatarController.updateAvatarFromRaw(
                    userId = userId,
                    bytes = call.request.receiveChannel().toByteArray(),
                    contentType = contentType,
                )

                contentType.match(ContentType.MultiPart.FormData) -> avatarController.updateAvatarFromMultiPart(
                    userId = userId,
                    multiPartData = call.receiveMultipart(),
                )

                else -> throw BadParametersException()
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}

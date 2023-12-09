package ru.droidcat.roomerfind.server.features.main_logic

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.droidcat.roomerfind.server.features.user.UserController

fun Application.configureLogicRouting() {

    routing {
        authenticate("auth-bearer") {

            get("/finders"){
                val logicController = LogicController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    logicController.getFinders(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            post("/react"){
                val logicController = LogicController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    logicController.react(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            get("/matches"){
                val logicController = LogicController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    logicController.getMatches(token) // Хз что лучше возвращать
                else
                    call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
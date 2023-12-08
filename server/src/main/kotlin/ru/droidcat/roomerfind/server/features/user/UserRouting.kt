package ru.droidcat.roomerfind.server.features.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUserRouting() {

    routing {
        authenticate("auth-bearer") {

            get("/user") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.getUserInfo(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            post("/user-edit") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.setUserInfo(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            get("/user-preferences") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.getRoommatePreferences(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            post("/user-preferences-edit") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.setRoommatePreferences(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            get("/user-geopos") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.getGeopositions(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            post("/user-geopos-add") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.addGeoposition(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            post("/user-geopos-remove") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.removeGeoposition(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            post("/user-geopos-clear") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.clearGeopositions(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            get ("user-contacts") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.getContactInfo(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            get ("user-contacts-by-id") {
                val userController = UserController(call)
                userController.getContactInfoById()
            }

            post ("user-contacts-edit") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.setContactInfo(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            get ("user-photo") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.getUserPhoto(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }

            get ("user-photo-edit") {
                val userController = UserController(call)
                val token = call.request.headers["Authorization"]
                    ?.split(" ")
                    ?.get(1)
                if (token != null)
                    userController.setUserPhoto(token)
                else
                    call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
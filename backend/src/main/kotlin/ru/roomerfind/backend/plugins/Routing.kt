package ru.roomerfind.backend.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import ru.roomerfind.backend.routing.authRoutes
import ru.roomerfind.backend.routing.avatarRoutes
import ru.roomerfind.backend.routing.finderRoutes
import ru.roomerfind.backend.routing.userInfoRoutes

fun Application.installRouting() {
    install(Routing) {
        authRoutes()
        userInfoRoutes()
        avatarRoutes()
        finderRoutes()
    }
}

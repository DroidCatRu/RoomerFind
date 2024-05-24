package ru.roomerfind.backend

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import ru.roomerfind.backend.plugins.installAuthentication
import ru.roomerfind.backend.plugins.installCORS
import ru.roomerfind.backend.plugins.installDI
import ru.roomerfind.backend.plugins.installHeaders
import ru.roomerfind.backend.plugins.installLogging
import ru.roomerfind.backend.plugins.installRouting
import ru.roomerfind.backend.plugins.installSerialization
import ru.roomerfind.backend.plugins.installStatusPages

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("unused")
fun Application.main() {
    installLogging()
    installDI()
    installSerialization()
    installCORS()
    installStatusPages()
    installAuthentication()
    installHeaders()
    installRouting()
}

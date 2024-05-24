package ru.roomerfind.backend.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.roomerfind.backend.di.mainModule
import ru.roomerfind.backend.di.userModule

fun Application.installDI() {
    install(Koin) {
        slf4jLogger()
        modules(
            mainModule,
            userModule,
        )
    }
}

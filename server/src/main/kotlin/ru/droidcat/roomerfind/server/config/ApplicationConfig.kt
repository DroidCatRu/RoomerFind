package ru.droidcat.roomerfind.server.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import ru.droidcat.roomerfind.server.serverModule
import javax.sql.DataSource

fun Application.module() {
    val dataSource by inject<DataSource>()

    install(Koin) {
        modules(serverModule)
    }

    routing {
    }
}

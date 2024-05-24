package ru.roomerfind.backend.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.callloging.processingTimeMillis
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import org.slf4j.event.Level

fun Application.installLogging() {
    install(CallLogging) {
        level = Level.INFO
        format { call ->
            listOf(
                call.response.status().toString(),
                call.request.httpMethod.value,
                call.request.path(),
                "User-Agent: ${call.request.headers["User-Agent"]}",
                "${call.processingTimeMillis()}ms",
            ).joinToString()
        }
    }
}

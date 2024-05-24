package ru.roomerfind.backend.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.partialcontent.PartialContent

fun Application.installHeaders() {
    install(PartialContent)
    install(AutoHeadResponse)
}
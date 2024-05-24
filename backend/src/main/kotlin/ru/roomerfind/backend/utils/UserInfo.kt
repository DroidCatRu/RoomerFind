package ru.roomerfind.backend.utils

import io.ktor.server.auth.jwt.JWTPrincipal
import ru.roomerfind.backend.plugins.NotAuthorizedException

fun getUserId(principal: JWTPrincipal?): Long =
    principal?.payload?.getClaim("id")?.asLong() ?: throw NotAuthorizedException()

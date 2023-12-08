package ru.droidcat.roomerfind.server.database.user_credentials

import org.jetbrains.exposed.dao.id.EntityID

class UserCredentialsDTO(
    val userId: Int,
    val login: String,
    val password: String,
)
package ru.droidcat.roomerfind.model.network

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthDTO(
    val email: String,
    val password: String,
)
